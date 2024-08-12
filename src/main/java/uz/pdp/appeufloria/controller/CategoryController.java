package uz.pdp.appeufloria.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appeufloria.payload.ApiResultDTO;
import uz.pdp.appeufloria.payload.in.CategoryCrudDTO;
import uz.pdp.appeufloria.service.CategoryService;
import uz.pdp.appeufloria.utils.AppConstants;


@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.APP_V1 + "/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all")
    public ResponseEntity<?> readAll() {
        return ResponseEntity.ok(categoryService.readAll());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/read/{id}")
    public ResponseEntity<?> read(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.read(id));
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.appeufloria.enums.PermissionEnum).CATEGORY_CREATE.name())")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid CategoryCrudDTO crudDTO) {
        return ResponseEntity.ok(categoryService.create(crudDTO));
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.appeufloria.enums.PermissionEnum).CATEGORY_UPDATE.name())")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid CategoryCrudDTO crudDTO, @PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.update(crudDTO, id));
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.appeufloria.enums.PermissionEnum).CATEGORY_DELETE.name())")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return ResponseEntity.ok(ApiResultDTO.success("Ok"));
    }
}


