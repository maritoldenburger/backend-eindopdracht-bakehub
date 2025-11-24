package nl.maritoldenburger.bakehub.mappers;

import nl.maritoldenburger.bakehub.dtos.ingredient.IngredientDto;
import nl.maritoldenburger.bakehub.dtos.ingredient.IngredientInputDto;
import nl.maritoldenburger.bakehub.models.Ingredient;

public class IngredientMapper {

    public static Ingredient toEntity (IngredientInputDto ingredientInputDto) {
        Ingredient ingredient = new Ingredient();

        ingredient.setName(ingredientInputDto.name);
        ingredient.setQuantity(ingredientInputDto.quantity);
        ingredient.setUnit(ingredientInputDto.unit);

        return ingredient;
    }

    public static IngredientDto toDto (Ingredient ingredient) {
        IngredientDto ingredientDto = new IngredientDto();

        ingredientDto.id = ingredient.getId();
        ingredientDto.name = ingredient.getName();
        ingredientDto.quantity = ingredient.getQuantity();
        ingredientDto.unit = ingredient.getUnit();

        return ingredientDto;
    }
}
