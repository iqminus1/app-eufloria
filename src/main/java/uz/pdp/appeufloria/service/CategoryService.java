package uz.pdp.appeufloria.service;

import uz.pdp.appeufloria.payload.ApiResultDTO;
import uz.pdp.appeufloria.payload.CategoryCrudDTO;

public interface CategoryService {
    ApiResultDTO<?> read(Integer id);
    ApiResultDTO<?> readAll();
    ApiResultDTO<?> create(CategoryCrudDTO crudDTO);
    ApiResultDTO<?> update(CategoryCrudDTO crudDTO,Integer id);
    void delete(Integer id);
}
