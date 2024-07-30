package up.pdp.appeufloria.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import up.pdp.appeufloria.payload.ApiResultDTO;
import up.pdp.appeufloria.payload.SignUpDTO;
import up.pdp.appeufloria.payload.SignInDTO;

public interface AuthService extends UserDetailsService {
    ApiResultDTO<?> signIn(SignInDTO signUp);

    ApiResultDTO<?> signUp(SignUpDTO signIn);

    ApiResultDTO<?> verifyEmail(String username, String code);
}
