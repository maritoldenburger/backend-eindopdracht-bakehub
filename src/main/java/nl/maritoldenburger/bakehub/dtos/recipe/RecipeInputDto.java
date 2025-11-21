package nl.maritoldenburger.bakehub.dtos.recipe;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import nl.maritoldenburger.bakehub.dtos.ingredient.IngredientInputDto;

import java.util.List;

public class RecipeInputDto {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 150)
    public String name;

    public String imageUrl;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 2000)
    public String description;

    @NotBlank(message = "Instructions are required")
    @Size(min = 20, max = 5000)
    public String instructions;

    @Min(value = 1, message = "Servings must be at least 1")
    @Max(value = 50, message = "Servings can't exceed 50")
    public int servings;

    @Min(value = 1, message = "Preparation time must be at least 1 minute")
    @Max(value = 1440, message = "Preparation time can't exceed 24 hours")
    public int preparationTime;

    @NotNull(message = "Category is required")
    public Long categoryId;

    @Valid
    @NotEmpty(message = "At least 1 ingredient is required")
    public List<IngredientInputDto> ingredients;
}
