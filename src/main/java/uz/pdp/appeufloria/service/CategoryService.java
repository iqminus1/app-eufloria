package uz.pdp.appeufloria.service;

import uz.pdp.appeufloria.payload.ApiResultDTO;
import uz.pdp.appeufloria.payload.in.CategoryCrudDTO;

public interface CategoryService {
    ApiResultDTO<?> readAll();
    ApiResultDTO<?> read(Integer id);
    ApiResultDTO<?> create(CategoryCrudDTO crudDTO);
    ApiResultDTO<?> update(CategoryCrudDTO crudDTO,Integer id);
    void delete(Integer id);
}
