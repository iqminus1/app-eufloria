package uz.pdp.appeufloria.service;

import uz.pdp.appeufloria.payload.ApiResultDTO;
import uz.pdp.appeufloria.payload.in.ProductCrudDTO;

public interface ProductService {
    ApiResultDTO<?> getAll();
    ApiResultDTO<?> getAllByCategoryId(Integer categoryId);

    ApiResultDTO<?> getAllByCategoryIdAndAvailable(Integer categoryId, boolean available);

    ApiResultDTO<?> read(Integer id);

    ApiResultDTO<?> create(ProductCrudDTO crudDTO);

    ApiResultDTO<?> update(Integer id, ProductCrudDTO crudDTO);

    void delete(Integer id);
}
