package uz.pdp.appeufloria.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import uz.pdp.appeufloria.entity.Attachment;
import uz.pdp.appeufloria.entity.Category;
import uz.pdp.appeufloria.entity.Product;
import uz.pdp.appeufloria.entity.UserFavourite;
import uz.pdp.appeufloria.payload.ApiResultDTO;
import uz.pdp.appeufloria.payload.ProductDTO;
import uz.pdp.appeufloria.payload.in.ProductCrudDTO;
import uz.pdp.appeufloria.repository.AttachmentRepository;
import uz.pdp.appeufloria.repository.CategoryRepository;
import uz.pdp.appeufloria.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static uz.pdp.appeufloria.utils.CommonUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final UserFavouriteService userFavouriteService;

    @Cacheable(value = "productAll")
    @Override
    public ApiResultDTO<?> getAll() {
        List<ProductDTO> products = productRepository.findAll().stream()
                .map(this::toDTO).toList();
        return ApiResultDTO.success(products);
    }

    @Cacheable(value = "productByCategoryId")
    @Override
    public ApiResultDTO<?> getAllByCategoryId(Integer categoryId) {
        Optional<UserFavourite> usersFavourite = userFavouriteService.getUsersFavourite(getCurrentUser());
        List<Product> productsByCategory = productRepository.findAllByCategoryId(categoryId);

        Map<Boolean, List<ProductDTO>> result = new HashMap<>();
        if (usersFavourite.isEmpty()) {
            List<ProductDTO> products = productsByCategory.stream()
                    .map(this::toDTO)
                    .toList();
            result.put(false, products);
            return ApiResultDTO.success(result);
        }
        List<Product> favouriteProducts = usersFavourite.get().getProducts().stream().filter(product -> product.getCategory().getId().equals(categoryId)).toList();
        productsByCategory.removeAll(favouriteProducts);
        result.putAll(Map.of(
                true, favouriteProducts.stream().map(this::toDTO).toList(),
                false, productsByCategory.stream().map(this::toDTO).toList()));
        return ApiResultDTO.success(result);
    }

    @Cacheable(value = "productByCategoryIdAndAvailable")
    @Override
    public ApiResultDTO<?> getAllByCategoryIdAndAvailable(Integer categoryId, boolean available) {
        List<ProductDTO> products = productRepository.findAllByCategoryIdAndAvailable(categoryId, available).stream()
                .map(this::toDTO)
                .toList();
        return ApiResultDTO.success(products);
    }


    @Override
    public ApiResultDTO<?> read(Integer id) {
        return ApiResultDTO.success(toDTO(productRepository.getById(id)));
    }

    @CacheEvict(value = {"productAll", "productByCategoryId", "productByCategoryIdAndAvailable"}, allEntries = true)
    @Override
    public ApiResultDTO<?> create(ProductCrudDTO crudDTO) {
        Product product = updateEntity(new Product(), crudDTO);

        productRepository.save(product);

        return ApiResultDTO.success(toDTO(product));
    }


    @CacheEvict(value = {"productAll", "productByCategoryId", "productByCategoryIdAndAvailable"}, allEntries = true)
    @Override
    public ApiResultDTO<?> update(Integer id, ProductCrudDTO crudDTO) {
        Product product = updateEntity(productRepository.getById(id), crudDTO);

        productRepository.save(product);

        return ApiResultDTO.success(toDTO(product));
    }

    @CacheEvict(value = {"productAll", "productByCategoryId", "productByCategoryIdAndAvailable"}, allEntries = true)
    @Override
    public void delete(Integer id) {
        Product product = productRepository.getById(id);
        productRepository.delete(product);
    }

    private ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setAvailable(product.isAvailable());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setAttachmentIds(product.getAttachments().stream().map(Attachment::getId).toList());
        return productDTO;
    }

    private Product updateEntity(Product product, ProductCrudDTO crudDTO) {
        product.setName(crudDTO.getName());
        product.setDescription(crudDTO.getDescription());
        product.setAvailable(crudDTO.isAvailable());

        Category category = categoryRepository.getById(crudDTO.getCategoryId());
        product.setCategory(category);

        List<Attachment> attachments = attachmentRepository.findAllById(crudDTO.getAttachmentIds());
        product.setAttachments(attachments);

        return product;
    }
}
