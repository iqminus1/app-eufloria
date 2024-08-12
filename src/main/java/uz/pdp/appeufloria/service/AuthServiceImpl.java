package uz.pdp.appeufloria.service;

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
import uz.pdp.appeufloria.entity.Code;
import uz.pdp.appeufloria.entity.User;
import uz.pdp.appeufloria.payload.ApiResultDTO;
import uz.pdp.appeufloria.payload.SignInDTO;
import uz.pdp.appeufloria.payload.in.SignUpDTO;
import uz.pdp.appeufloria.payload.TokenDTO;
import uz.pdp.appeufloria.repository.CodeRepository;
import uz.pdp.appeufloria.repository.UserRepository;
import uz.pdp.appeufloria.security.JwtProvider;
import uz.pdp.appeufloria.utils.CommonUtils;

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
    public ApiResultDTO<?> signIn(SignInDTO signIn) {
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    signIn.getUsername(),
                    signIn.getPassword()
            );
            authenticationProvider.authenticate(authentication);
            String token = jwtProvider.generateToken(signIn.getUsername());
            return ApiResultDTO.success(new TokenDTO(token));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    public ApiResultDTO<?> signUp(SignUpDTO signUp) {
        if (!Objects.equals(signUp.getPassword(), signUp.getAcceptedPassword())) {
            throw new BadRequestException();
        }
        User user = new User(
                signUp.getUsername(),
                passwordEncoder.encode(signUp.getPassword()),
                signUp.getEmail(),
                false,
                commonUtils.getUserRole()
        );
        userRepository.save(user);
        mailService.sendVerify(signUp.getEmail(),signUp.getUsername());
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
