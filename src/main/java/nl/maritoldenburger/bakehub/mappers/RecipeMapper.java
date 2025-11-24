package nl.maritoldenburger.bakehub.mappers;

import nl.maritoldenburger.bakehub.dtos.ingredient.IngredientDto;
import nl.maritoldenburger.bakehub.dtos.recipe.RecipeDto;
import nl.maritoldenburger.bakehub.dtos.recipe.RecipeInputDto;
import nl.maritoldenburger.bakehub.dtos.review.ReviewDto;
import nl.maritoldenburger.bakehub.models.Ingredient;
import nl.maritoldenburger.bakehub.models.Recipe;
import nl.maritoldenburger.bakehub.models.Review;

import java.util.ArrayList;
import java.util.List;

public class RecipeMapper {

    public static Recipe toEntity(RecipeInputDto recipeInputDto) {
        Recipe recipe = new Recipe();

        recipe.setName(recipeInputDto.name);
        recipe.setImageUrl(recipeInputDto.imageUrl);
        recipe.setDescription(recipeInputDto.description);
        recipe.setInstructions(recipeInputDto.instructions);
        recipe.setServings(recipeInputDto.servings);
        recipe.setPreparationTime(recipeInputDto.preparationTime);

        return recipe;
    }

    public static RecipeDto toDto(Recipe recipe) {
        RecipeDto recipeDto = new RecipeDto();

        recipeDto.id = recipe.getId();
        recipeDto.name = recipe.getName();
        recipeDto.imageUrl = recipe.getImageUrl();
        recipeDto.description = recipe.getDescription();
        recipeDto.instructions = recipe.getInstructions();
        recipeDto.servings = recipe.getServings();
        recipeDto.preparationTime = recipe.getPreparationTime();
        recipeDto.rating = recipe.getRating();

        if (recipe.getCategory() != null) {
            recipeDto.category = CategoryMapper.toDto(recipe.getCategory());
        }

        List<IngredientDto> ingredientDto = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredientDto.add(IngredientMapper.toDto(ingredient));
        }
        recipeDto.ingredients = ingredientDto;

        List<ReviewDto> reviewDto = new ArrayList<>();
        for (Review review : recipe.getReviews()) {
            reviewDto.add(ReviewMapper.toDto(review));
        }
        recipeDto.reviews = reviewDto;

        return recipeDto;
    }
}
