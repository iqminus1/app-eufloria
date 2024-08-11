package uz.pdp.appeufloria.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appeufloria.entity.Category;
import uz.pdp.appeufloria.exceptions.NotFoundException;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Cacheable(value = "categoryEntity", key = "#id")
    default Category getById(Integer id) {
        return findById(id).orElseThrow(() -> NotFoundException.errorById("Category", id));
    }

    @CachePut(value = "categoryEntity", key = "#result.id")
    @Override
    Category save(Category category);

    @CacheEvict(value = "categoryEntity", key = "#category.id")
    @Override
    void delete(Category category);
}