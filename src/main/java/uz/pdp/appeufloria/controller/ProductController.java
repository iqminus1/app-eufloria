package uz.pdp.appeufloria.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/byCId/{id}")
    public ResponseEntity<?> getAllByCategoryId(@PathVariable(name = "id") Integer categoryId) {
        return ResponseEntity.ok(productService.getAllByCategoryId(categoryId));
    }

    @GetMapping("/byCIdAndA/{id}")
    public ResponseEntity<?> getAllByCategoryIdAndAvailable(@PathVariable(name = "id") Integer categoryId, @RequestParam boolean available) {
        return ResponseEntity.ok(productService.getAllByCategoryIdAndAvailable(categoryId, available));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<?> read(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.read(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody ProductCrudDTO crudDTO) {
        return ResponseEntity.ok(productService.create(crudDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ProductCrudDTO crudDTO, @PathVariable Integer id) {
        return ResponseEntity.ok(productService.update(id, crudDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.ok(ApiResultDTO.success("Ok"));
    }

}
