package uz.pdp.appeufloria.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import uz.pdp.appeufloria.entity.Category;
import uz.pdp.appeufloria.payload.ApiResultDTO;
import uz.pdp.appeufloria.payload.in.CategoryCrudDTO;
import uz.pdp.appeufloria.payload.CategoryDTO;
import uz.pdp.appeufloria.repository.CategoryRepository;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Cacheable(value = "categoryAll")
    public ApiResultDTO<?> readAll() {
        List<CategoryDTO> categories = categoryRepository.findAll().stream().map(this::toDTO).toList();
        return ApiResultDTO.success(categories);
    }

    @Override
    public ApiResultDTO<?> read(Integer id) {
        Category category = categoryRepository.getById(id);
        return ApiResultDTO.success(toDTO(category));
    }

    @Override
    @CacheEvict(value = "categoryAll", allEntries = true)
    public ApiResultDTO<?> create(CategoryCrudDTO crudDTO) {
        Category parentCategory = null;
        if (Objects.nonNull(crudDTO.getParentCategoryId())) {
            parentCategory = categoryRepository.getById(crudDTO.getParentCategoryId());
        }
        Category category = new Category(crudDTO.getName(), parentCategory);
        categoryRepository.save(category);
        return ApiResultDTO.success(toDTO(category));
    }

    @Override
    @CacheEvict(value = "categoryAll", allEntries = true)
    public ApiResultDTO<?> update(CategoryCrudDTO crudDTO, Integer id) {
        Category parentCategory = null;
        if (Objects.nonNull(crudDTO.getParentCategoryId())) {
            parentCategory = categoryRepository.getById(crudDTO.getParentCategoryId());
        }
        Category category = categoryRepository.getById(id);
        category.setParentCategory(parentCategory);
        category.setName(crudDTO.getName());
        categoryRepository.save(category);
        return ApiResultDTO.success(toDTO(category));
    }

    @Override
    @CacheEvict(value = "categoryAll", allEntries = true)
    public void delete(Integer id) {
        Category category = categoryRepository.getById(id);
        categoryRepository.delete(category);
    }


    private CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getName(), null);
        if (Objects.nonNull(category.getParentCategory())) {
            categoryDTO.setParentCategoryId(category.getParentCategory().getId());
        }
        return categoryDTO;
    }
}
