package nl.maritoldenburger.bakehub.mappers;

import nl.maritoldenburger.bakehub.dtos.favorite.FavoriteDto;
import nl.maritoldenburger.bakehub.models.Favorite;

public class FavoriteMapper {

    public static FavoriteDto toFavoriteDto(Favorite favorite) {
        FavoriteDto favoriteDto = new FavoriteDto();

        favoriteDto.id = favorite.getId();
        favoriteDto.userId = favorite.getUser().getId();
        favoriteDto.recipeId = favorite.getRecipe().getId();

        return favoriteDto;
    }
}
