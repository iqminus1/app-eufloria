package uz.pdp.appeufloria.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appeufloria.payload.SignInDTO;
import uz.pdp.appeufloria.payload.in.SignUpDTO;
import uz.pdp.appeufloria.service.AuthService;
import uz.pdp.appeufloria.utils.AppConstants;

@RequestMapping(AppConstants.APP_V1 + "/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDTO signUpDTO) {
        return ResponseEntity.status(200).body(authService.signUp(signUpDTO));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInDTO signIn) {
        return ResponseEntity.status(200).body(authService.signIn(signIn));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam String username, @RequestParam String code) {
        return ResponseEntity.status(200).body(authService.verifyEmail(username, code));
    }
    @GetMapping("/flush-usernames-token")
    @CacheEvict(value = "tokenGenerate",key = "#username")
    public ResponseEntity<?> flush(@RequestParam String username){
        return ResponseEntity.status(200).body("Ok");
    }
}
