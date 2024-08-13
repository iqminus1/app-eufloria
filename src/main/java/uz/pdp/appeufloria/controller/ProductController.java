package uz.pdp.appeufloria.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appeufloria.payload.ApiResultDTO;
import uz.pdp.appeufloria.payload.in.ProductCrudDTO;
import uz.pdp.appeufloria.service.ProductService;
import uz.pdp.appeufloria.utils.AppConstants;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.APP_V1 + "/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/readAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(productService.getAll());
    }

//    @PreAuthorize("isAuthenticated()")
    @GetMapping("/byCId/{id}")
    public ResponseEntity<?> getAllByCategoryId(@PathVariable(name = "id") Integer categoryId) {
        return ResponseEntity.ok(productService.getAllByCategoryId(categoryId));
    }

//    @PreAuthorize("isAuthenticated()")
    @GetMapping("/byCIdAndA/{id}")
    public ResponseEntity<?> getAllByCategoryIdAndAvailable(@PathVariable(name = "id") Integer categoryId, @RequestParam boolean available) {
        return ResponseEntity.ok(productService.getAllByCategoryIdAndAvailable(categoryId, available));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<?> read(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.read(id));
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.appeufloria.enums.PermissionEnum).PRODUCT_CREATE.name())")
    @PostMapping("/create")
    public ResponseEntity<?> create( @RequestBody @Valid ProductCrudDTO crudDTO) {
        return ResponseEntity.ok(productService.create(crudDTO));
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.appeufloria.enums.PermissionEnum).PRODUCT_UPDATE.name())")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update( @RequestBody @Valid ProductCrudDTO crudDTO, @PathVariable Integer id) {
        return ResponseEntity.ok(productService.update(id, crudDTO));
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.appeufloria.enums.PermissionEnum).PRODUCT_DELETE.name())")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.ok(ApiResultDTO.success("Ok"));
    }

}
