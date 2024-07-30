package up.pdp.appeufloria.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import up.pdp.appeufloria.entity.Role;
import up.pdp.appeufloria.repository.RoleRepository;

@RequiredArgsConstructor
@Component
public class CommonUtils {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.getByName(AppConstants.ROLE_USER_STRING);
    }
}
