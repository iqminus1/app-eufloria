package up.pdp.appeufloria.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import up.pdp.appeufloria.entity.Role;
import up.pdp.appeufloria.entity.Role;
import up.pdp.appeufloria.exceptions.NotFoundException;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);

    @Cacheable(value = "roleEntity", key = "#name")
    default Role getByName(String name) {
        return findByName(name).orElseThrow(() -> new NotFoundException("Role not found by name -> " + name));
    }

    @Override
    @CachePut(value = "roleEntity",key = "#result.name")
    Role save(Role role);

    @Override
    @CacheEvict(value = "roleEntity",key = "#role.name")
    void delete(Role role);
}