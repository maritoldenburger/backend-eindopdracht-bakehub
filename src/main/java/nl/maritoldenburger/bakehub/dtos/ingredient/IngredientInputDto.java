package nl.maritoldenburger.bakehub.dtos.ingredient;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import nl.maritoldenburger.bakehub.enums.Unit;

public class IngredientInputDto {

    @NotBlank(message = "Name is required")
    @Size(min=2, max=128)
    public String name;

    @DecimalMin(value = "0.01", message = "Quantity must be more than 0")
    public Double quantity;

    public Unit unit;
}
