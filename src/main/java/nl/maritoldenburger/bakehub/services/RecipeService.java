package nl.maritoldenburger.bakehub.services;

import nl.maritoldenburger.bakehub.exceptions.RecordNotFoundException;
import nl.maritoldenburger.bakehub.models.Category;
import nl.maritoldenburger.bakehub.models.Recipe;
import nl.maritoldenburger.bakehub.repositories.CategoryRepository;
import nl.maritoldenburger.bakehub.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;

    public RecipeService(RecipeRepository recipeRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Recipe " + id + " not found"));
    }

    public List<Recipe> getRecipesByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RecordNotFoundException("Category " + categoryId + " not found"));
        return category.getRecipes();
    }

    //todo
    //zoekfunctie toevoegen?

    public Recipe addRecipe(Recipe recipe, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RecordNotFoundException("Category " + categoryId + " not found"));

        recipe.setCategory(category);
        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(Long id, Recipe updated) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Recipe " + id + " not found"));

        recipe.setName(updated.getName());
        recipe.setImageUrl(updated.getImageUrl());
        recipe.setDescription(updated.getDescription());
        recipe.setInstructions(updated.getInstructions());
        recipe.setServings(updated.getServings());
        recipe.setPreparationTime(updated.getPreparationTime());

        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Recipe " + id + " not found"));
        recipeRepository.delete(recipe);
    }

    public void updateRecipeRating(Recipe recipe) {
        double avg = recipe.getReviews()
                .stream()
                .mapToInt(r -> r.getRating())
                .average()
                .orElse(0.0);

        recipe.setRating(avg);
        recipeRepository.save(recipe);
    }
}
