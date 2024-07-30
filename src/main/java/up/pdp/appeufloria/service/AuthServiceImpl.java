package up.pdp.appeufloria.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import up.pdp.appeufloria.entity.Code;
import up.pdp.appeufloria.entity.User;
import up.pdp.appeufloria.payload.ApiResultDTO;
import up.pdp.appeufloria.payload.SignInDTO;
import up.pdp.appeufloria.payload.SignUpDTO;
import up.pdp.appeufloria.payload.TokenDTO;
import up.pdp.appeufloria.repository.CodeRepository;
import up.pdp.appeufloria.repository.UserRepository;
import up.pdp.appeufloria.security.JwtProvider;
import up.pdp.appeufloria.utils.CommonUtils;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final CommonUtils commonUtils;
    private final PasswordEncoder passwordEncoder;
    private final CodeRepository codeRepository;
    private final AuthenticationProvider authenticationProvider;
    private final JwtProvider jwtProvider;

    @Cacheable(value = "user", key = "#username")
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getByUsername(username);
    }

    @Override
    public ApiResultDTO<?> signIn(SignInDTO signUp) {
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    signUp.getUsername(),
                    signUp.getPassword()
            );
            authenticationProvider.authenticate(authentication);
            String token = jwtProvider.generateToken(signUp.getUsername());
            return ApiResultDTO.success(new TokenDTO(token));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    public ApiResultDTO<?> signUp(SignUpDTO signIn) {
        if (!Objects.equals(signIn.getPassword(), signIn.getAcceptedPassword())) {
            throw new BadRequestException();
        }
        User user = new User(
                signIn.getUsername(),
                passwordEncoder.encode(signIn.getPassword()),
                signIn.getEmail(),
                false,
                commonUtils.getUserRole()
        );
        userRepository.save(user);
        mailService.sendVerify(signIn.getEmail(),signIn.getUsername());
        return ApiResultDTO.success("Verify email");
    }

    @Override
    public ApiResultDTO<?> verifyEmail(String username, String code) {
        User user = userRepository.getByUsername(username);

        Code codeEntity = codeRepository.getByEmail(user.getEmail());

        if (!codeEntity.getCodeString().equals(code)) {
            if (codeEntity.getAttempt() == 1) {
                codeRepository.delete(codeEntity);
                return ApiResultDTO.error("Your attempt ended");
            }
            codeEntity.setAttempt(codeEntity.getAttempt() - 1);
            codeRepository.save(codeEntity);
            return ApiResultDTO.error("Code not equals");
        }

        codeRepository.delete(codeEntity);

        user.setEnable(true);
        userRepository.save(user);

        return ApiResultDTO.success("Ok");
    }

}
