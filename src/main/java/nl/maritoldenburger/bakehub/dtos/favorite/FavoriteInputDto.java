package nl.maritoldenburger.bakehub.dtos.favorite;

import jakarta.validation.constraints.NotNull;

public class FavoriteInputDto {

    public Long userId;

    @NotNull(message = "recipe is required")
    public Long recipeId;
}
