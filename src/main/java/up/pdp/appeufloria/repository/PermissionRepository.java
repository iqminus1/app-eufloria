package up.pdp.appeufloria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import up.pdp.appeufloria.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}