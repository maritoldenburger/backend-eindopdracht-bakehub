package nl.maritoldenburger.bakehub.services;

import nl.maritoldenburger.bakehub.exceptions.RecordNotFoundException;
import nl.maritoldenburger.bakehub.models.Ingredient;
import nl.maritoldenburger.bakehub.models.Recipe;
import nl.maritoldenburger.bakehub.repositories.IngredientRepository;
import nl.maritoldenburger.bakehub.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    public IngredientService(IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredient(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Ingredient " + id + " not found"));
    }

    public Ingredient addIngredient(Ingredient ingredient, Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecordNotFoundException("Recipe " + recipeId + " not found"));

        ingredient.setRecipe(recipe);
        return ingredientRepository.save(ingredient);
    }

    public Ingredient updateIngredient(Long id, Ingredient updated) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Ingredient " + id + " not found"));

        ingredient.setName(updated.getName());
        ingredient.setQuantity(updated.getQuantity());
        ingredient.setUnit(updated.getUnit());

        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Ingredient " + id + " not found"));
        ingredientRepository.delete(ingredient);
    }
}