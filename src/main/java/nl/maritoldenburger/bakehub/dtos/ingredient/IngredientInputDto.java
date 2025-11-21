package nl.maritoldenburger.bakehub.dtos.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import nl.maritoldenburger.bakehub.enums.Unit;

public class IngredientInputDto {

    @NotBlank(message = "Name is required")
    @Size(min=2, max=128)
    public String name;

    public Double quantity;

    public Unit unit;

    public Long recipeId;
}
