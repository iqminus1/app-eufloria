package uz.pdp.appeufloria.repository;

import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appeufloria.entity.User;
import uz.pdp.appeufloria.entity.UserFavourite;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFavouriteRepository extends JpaRepository<UserFavourite, Integer> {
    @Cacheable(value = "userFavourite",key = "#user.id")
    Optional<UserFavourite> findByUser(User user);

    @CachePut(value = "userFavourite",key = "#userFavourite.user.id")
    @Override
    UserFavourite save(UserFavourite userFavourite);

    @CacheEvict(value = "userFavourite",key = "#userFavourite.user.id")
    void delete(UserFavourite userFavourite);
}