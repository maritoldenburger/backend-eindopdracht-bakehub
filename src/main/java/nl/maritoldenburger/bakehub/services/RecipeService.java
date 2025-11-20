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

    public Recipe getRecipe(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Recipe " + id + " not found"));
    }

    public List getRecipesByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new RecordNotFoundException("Category " + categoryId + " not found"));
        return category.getRecipes();
    }
}
