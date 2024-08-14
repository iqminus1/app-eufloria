package uz.pdp.appeufloria.service;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.pdp.appeufloria.entity.Product;
import uz.pdp.appeufloria.entity.User;
import uz.pdp.appeufloria.entity.UserFavourite;
import uz.pdp.appeufloria.payload.ApiResultDTO;
import uz.pdp.appeufloria.repository.ProductRepository;
import uz.pdp.appeufloria.repository.UserFavouriteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static uz.pdp.appeufloria.utils.CommonUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
public class UserFavouriteServiceImpl implements UserFavouriteService {
    private final ProductRepository productRepository;
    private final UserFavouriteRepository userFavouriteRepository;

    private final ConcurrentMap<User, List<Product>> unFavourite = new ConcurrentHashMap<>();

    @Override
    public Optional<UserFavourite> getUsersFavourite(User user) {
        Optional<UserFavourite> byUser = userFavouriteRepository.findByUser(user);
        if (byUser.isEmpty()) {
            return Optional.empty();
        }
        UserFavourite userFavourite = byUser.get();
        List<Product> productsToRemove = unFavourite.get(user);
        if (productsToRemove != null) {
            userFavourite.getProducts().removeAll(productsToRemove);
        }
        return Optional.of(userFavourite);
    }

    @Override
    public ApiResultDTO<?> add(Integer productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ApiResultDTO.error("Product not found");
        }
        User currentUser = getCurrentUser();

        List<Product> unFavouriteProducts = unFavourite.get(currentUser);
        if (unFavouriteProducts != null && unFavouriteProducts.contains(product)) {
            unFavouriteProducts.remove(product);
            return ApiResultDTO.success("Ok");
        }

        Optional<UserFavourite> byUser = userFavouriteRepository.findByUser(currentUser);
        if (byUser.isPresent()) {
            UserFavourite userFavourite = byUser.get();
            userFavourite.getProducts().add(product);
            userFavouriteRepository.save(userFavourite);
            return ApiResultDTO.success("Ok");
        }

        UserFavourite userFavourite = new UserFavourite(currentUser, List.of(product));
        userFavouriteRepository.save(userFavourite);
        return ApiResultDTO.success("Ok");
    }

    //Database ga bosimni kamaytirish uchun map ga yeg`ib olib schaduled orqali har xx:xx da ochirib
    //tashlanishi uchun qilingan
    @Override
    public ApiResultDTO<?> delete(Integer productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ApiResultDTO.error("Product not found");
        }
        User currentUser = getCurrentUser();

        unFavourite.computeIfAbsent(currentUser, k -> new ArrayList<>()).add(product);
        return ApiResultDTO.success("Ok");
    }

    @CacheEvict(value = {"productAll", "productByCategoryId", "productByCategoryIdAndAvailable"}, allEntries = true)
    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.MINUTES)
    public void usersUnFavourite() {
        for (Map.Entry<User, List<Product>> entry : unFavourite.entrySet()) {
            User user = entry.getKey();
            List<Product> products = entry.getValue();

            Optional<UserFavourite> byUser = userFavouriteRepository.findByUser(user);
            if (byUser.isEmpty()) {
                continue;
            }

            UserFavourite userFavourite = byUser.get();
            userFavourite.getProducts().removeAll(products);
            userFavouriteRepository.save(userFavourite);
        }
        unFavourite.clear();
    }

    @PreDestroy
    public void preDestroy() {
        usersUnFavourite();
    }
}
