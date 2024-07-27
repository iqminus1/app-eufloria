package up.pdp.appeufloria.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import up.pdp.appeufloria.entity.User;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class AuditingConfig implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.isAuthenticated())
            return Optional.ofNullable(((User) authentication.getPrincipal()).getId());
        return Optional.empty();
    }
}
