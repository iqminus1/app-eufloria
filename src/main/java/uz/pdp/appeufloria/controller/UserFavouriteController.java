package uz.pdp.appeufloria.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appeufloria.service.UserFavouriteService;
import uz.pdp.appeufloria.utils.AppConstants;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.APP_V1 + "/userFavourite")
public class UserFavouriteController {
    private final UserFavouriteService userFavouriteService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add/{categoryId}")
    public ResponseEntity<?> add(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(userFavouriteService.add(categoryId));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/remove/{categoryId}")
    public ResponseEntity<?> remove(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(userFavouriteService.delete(categoryId));
    }
}
