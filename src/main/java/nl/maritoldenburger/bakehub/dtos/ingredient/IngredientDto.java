package nl.maritoldenburger.bakehub.dtos.ingredient;

import nl.maritoldenburger.bakehub.enums.Unit;

public class IngredientDto {
    public Long id;
    public String name;
    public Double quantity;
    public Unit unit;
    public Long recipeId;

}
