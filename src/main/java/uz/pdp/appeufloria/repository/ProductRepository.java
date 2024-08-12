package uz.pdp.appeufloria.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appeufloria.entity.Product;
import uz.pdp.appeufloria.exceptions.NotFoundException;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Cacheable(value = "productEntity", key = "#id")
    default Product getById(Integer id) {
        return findById(id).orElseThrow(() -> NotFoundException.errorById("Product", id));
    }

    @CachePut(value = "productEntity", key = "#result.id")
    @Override
    Product save(Product product);

    @CacheEvict(value = "productEntity", key = "#product.id")
    @Override
    void delete(Product product);
}