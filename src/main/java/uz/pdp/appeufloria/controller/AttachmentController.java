package uz.pdp.appeufloria.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appeufloria.service.AttachmentService;
import uz.pdp.appeufloria.utils.AppConstants;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.APP_V1 + "/attachment")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/read/{id}")
    public void read(@PathVariable Integer id, HttpServletResponse resp) {
        attachmentService.read(resp, id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority(T(uz.pdp.appeufloria.enums.PermissionEnum).ATTACHMENT_CREATE.name())")
    public ResponseEntity<?> create(HttpServletRequest req) {
        return ResponseEntity.status(200).body(attachmentService.create(req));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority(T(uz.pdp.appeufloria.enums.PermissionEnum).ATTACHMENT_UPDATE.name())")
    public ResponseEntity<?> update(HttpServletRequest req, @PathVariable Integer id) {
        return ResponseEntity.status(200).body(attachmentService.update(req, id));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority(T(uz.pdp.appeufloria.enums.PermissionEnum).ATTACHMENT_DELETE.name())")
    public void delete(@PathVariable Integer id) {
        attachmentService.delete(id);
    }
}
