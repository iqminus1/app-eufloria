package up.pdp.appeufloria.init;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import up.pdp.appeufloria.entity.Permission;
import up.pdp.appeufloria.entity.Role;
import up.pdp.appeufloria.entity.User;
import up.pdp.appeufloria.enums.PermissionEnum;
import up.pdp.appeufloria.enums.RoleType;
import up.pdp.appeufloria.repository.PermissionRepository;
import up.pdp.appeufloria.repository.RoleRepository;
import up.pdp.appeufloria.repository.UserRepository;

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
    }

    private void checkAdmin() {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent())
            return;
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

        List<String> list = permissions.stream().map(p -> p.getName()).toList();

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
