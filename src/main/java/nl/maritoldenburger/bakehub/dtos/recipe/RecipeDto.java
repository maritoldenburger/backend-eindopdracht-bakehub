package nl.maritoldenburger.bakehub.dtos.recipe;

import nl.maritoldenburger.bakehub.dtos.ingredient.IngredientDto;
import nl.maritoldenburger.bakehub.dtos.review.ReviewDto;
import nl.maritoldenburger.bakehub.dtos.category.CategoryDto;

import java.util.List;

public class RecipeDto {
    public Long id;
    public String name;
    public String imageUrl;
    public String description;
    public String instructions;
    public int servings;
    public int preparationTime;
    public Double rating;
    public CategoryDto category;
    public List<IngredientDto> ingredients;
    public List<ReviewDto> reviews;
}
