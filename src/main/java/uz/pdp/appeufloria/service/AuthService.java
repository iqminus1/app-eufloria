package uz.pdp.appeufloria.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import uz.pdp.appeufloria.payload.ApiResultDTO;
import uz.pdp.appeufloria.payload.in.SignUpDTO;
import uz.pdp.appeufloria.payload.SignInDTO;

public interface AuthService extends UserDetailsService {
    ApiResultDTO<?> signIn(SignInDTO signUp);

    ApiResultDTO<?> signUp(SignUpDTO signIn);

    ApiResultDTO<?> verifyEmail(String username, String code);
}
