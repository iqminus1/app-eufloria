package up.pdp.appeufloria.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import up.pdp.appeufloria.entity.User;
import up.pdp.appeufloria.exceptions.NotFoundException;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Cacheable(value = "userEntity", key = "#username")
    default User getByUsername(String username) {
        return findByUsername(username).orElseThrow(() -> new NotFoundException("User not found by username -> " + username));
    }

    @Override
    @CachePut(value = "userEntity",key = "#result.username")
    User save(User user);

    @Override
    @CacheEvict(value = "userEntity",key = "#user.username")
    void delete(User user);
}