package uz.pdp.appeufloria.service;

import uz.pdp.appeufloria.entity.User;
import uz.pdp.appeufloria.entity.UserFavourite;
import uz.pdp.appeufloria.payload.ApiResultDTO;

import java.util.Optional;

public interface UserFavouriteService {
    Optional<UserFavourite> getUsersFavourite(User user);
    ApiResultDTO<?> add(Integer productId);

    ApiResultDTO<?> delete(Integer productId);
}
