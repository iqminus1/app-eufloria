package uz.pdp.appeufloria.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.pdp.appeufloria.entity.Role;
import uz.pdp.appeufloria.entity.User;
import uz.pdp.appeufloria.repository.RoleRepository;

@RequiredArgsConstructor
@Component
public class CommonUtils {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.getByName(AppConstants.ROLE_USER_STRING);
    }
    public static User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
