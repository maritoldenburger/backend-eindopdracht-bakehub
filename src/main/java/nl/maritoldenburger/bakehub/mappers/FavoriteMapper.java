package nl.maritoldenburger.bakehub.mappers;

import nl.maritoldenburger.bakehub.dtos.favorite.FavoriteDto;
import nl.maritoldenburger.bakehub.models.Favorite;

public class FavoriteMapper {

    public static FavoriteDto toDto(Favorite favorite) {
        FavoriteDto favoriteDto = new FavoriteDto();

        favoriteDto.id = favorite.getId();
        favoriteDto.recipe = RecipeMapper.toDto(favorite.getRecipe());

        return favoriteDto;
    }
}
