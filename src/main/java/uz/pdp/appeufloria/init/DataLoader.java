package uz.pdp.appeufloria.init;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appeufloria.entity.Permission;
import uz.pdp.appeufloria.entity.Role;
import uz.pdp.appeufloria.entity.User;
import uz.pdp.appeufloria.enums.PermissionEnum;
import uz.pdp.appeufloria.enums.RoleType;
import uz.pdp.appeufloria.repository.PermissionRepository;
import uz.pdp.appeufloria.repository.RoleRepository;
import uz.pdp.appeufloria.repository.UserRepository;
import uz.pdp.appeufloria.utils.AppConstants;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private String username;
    @Value("${app.admin.password}")
    private String password;
    @Value("${app.admin.email}")
    private String email;


    @Override
    public void run(String... args) {
        checkAdmin();

        checkUserRole();
    }

    private void checkUserRole() {
        if (roleRepository.findByName(AppConstants.ROLE_USER_STRING).isPresent()) {
            return;
        }
        Permission userPerm = permissionRepository.findAll()
                .stream()
                .filter(e -> e.getPermission().equals(PermissionEnum.USER_PERMISSION))
                .findFirst()
                .orElseGet(() -> {
                    Permission permission = new Permission(PermissionEnum.USER_PERMISSION.name(), PermissionEnum.USER_PERMISSION);
                    permissionRepository.save(permission);
                    return permission;
                });
        Role role = new Role(AppConstants.ROLE_USER_STRING, RoleType.USER, List.of(userPerm));
        roleRepository.save(role);
    }

    private void checkAdmin() {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getRole().setPermissions(getPermissions());
            roleRepository.save(user.getRole());
            userRepository.save(user);
        }
        Role role = getRole();
        User user = new User(username, passwordEncoder.encode(password), email, true, role);
        userRepository.save(user);

    }

    private Role getRole() {
        return roleRepository.findByName("Admin").orElseGet(() -> {
            List<Permission> permissions = getPermissions();
            Role role = new Role("Admin", RoleType.ADMIN, permissions);
            roleRepository.save(role);
            return role;
        });
    }

    private List<Permission> getPermissions() {
        List<Permission> permissions = permissionRepository.findAll();

        if (permissions.size() == PermissionEnum.values().length) {
            return permissions;
        }

        List<String> list = permissions.stream().map(Permission::getName).toList();

        for (PermissionEnum value : PermissionEnum.values()) {
            if (list.contains(value)) {
                continue;
            }
            Permission permission = new Permission(value.name(), value);
            permissionRepository.save(permission);
            permissions.add(permission);
        }
        return permissions;
    }
}
