package nl.maritoldenburger.bakehub.services;

import nl.maritoldenburger.bakehub.dtos.recipe.RecipeDto;
import nl.maritoldenburger.bakehub.dtos.recipe.RecipeInputDto;
import nl.maritoldenburger.bakehub.exceptions.RecordNotFoundException;
import nl.maritoldenburger.bakehub.mappers.RecipeMapper;
import nl.maritoldenburger.bakehub.models.Category;
import nl.maritoldenburger.bakehub.models.Recipe;
import nl.maritoldenburger.bakehub.models.Review;
import nl.maritoldenburger.bakehub.repositories.CategoryRepository;
import nl.maritoldenburger.bakehub.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;

    public RecipeService(RecipeRepository recipeRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<RecipeDto> getAllRecipes() {

        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDto> dtoRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            dtoRecipes.add(RecipeMapper.toDto(recipe));
        }
        return dtoRecipes;
    }

    public RecipeDto getRecipeById(Long id) {

        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Recipe " + id + " not found"));

        return RecipeMapper.toDto(recipe);
    }

    public List<RecipeDto> getRecipesByCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RecordNotFoundException("Category " + categoryId + " not found"));

        List<RecipeDto> dtoRecipes = new ArrayList<>();

        for (Recipe recipe : category.getRecipes()) {
            dtoRecipes.add(RecipeMapper.toDto(recipe));
        }
        return dtoRecipes;
    }

    public List<RecipeDto> searchRecipe(String query) {

        List<Recipe> recipes = recipeRepository.findByNameContainingIgnoreCase(query);
        List<RecipeDto> foundRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            foundRecipes.add(RecipeMapper.toDto(recipe));
        }
        return foundRecipes;
    }

    public RecipeDto addRecipe(RecipeInputDto recipeInputDto, Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RecordNotFoundException("Category " + categoryId + " not found"));

        Recipe recipe = RecipeMapper.toEntity(recipeInputDto);
        recipe.setCategory(category);
        Recipe savedRecipe = recipeRepository.save(recipe);

        return RecipeMapper.toDto(savedRecipe);
    }

    public RecipeDto updateRecipe(Long id, RecipeInputDto recipeInputDto) {

        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Recipe " + id + " not found"));

        recipe.setName(recipeInputDto.name);
        recipe.setImageUrl(recipeInputDto.imageUrl);
        recipe.setDescription(recipeInputDto.description);
        recipe.setInstructions(recipeInputDto.instructions);
        recipe.setServings(recipeInputDto.servings);
        recipe.setPreparationTime(recipeInputDto.preparationTime);

        Recipe updatedRecipe = recipeRepository.save(recipe);

        return RecipeMapper.toDto(updatedRecipe);
    }

    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Recipe " + id + " not found"));
        recipeRepository.delete(recipe);
    }

    public void updateRecipeRating(Recipe recipe) {

        List<Review> reviews = recipe.getReviews();

        if (reviews.isEmpty()) {
            recipe.setRating(0.0);
        } else {
            int totalRating = 0;

            for (Review review : reviews) {
                totalRating += review.getRating();
            }

            double average = totalRating / reviews.size();
            recipe.setRating(average);
        }
        recipeRepository.save(recipe);
    }
}
