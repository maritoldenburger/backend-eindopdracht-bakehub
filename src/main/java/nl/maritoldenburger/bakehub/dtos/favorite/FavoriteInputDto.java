package nl.maritoldenburger.bakehub.dtos.favorite;

import jakarta.validation.constraints.NotNull;

public class FavoriteInputDto {

    @NotNull(message = "Recipe is required")
    public Long recipeId;
}
