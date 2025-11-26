package nl.maritoldenburger.bakehub.services;

import nl.maritoldenburger.bakehub.dtos.ingredient.IngredientDto;
import nl.maritoldenburger.bakehub.dtos.ingredient.IngredientInputDto;
import nl.maritoldenburger.bakehub.exceptions.RecordNotFoundException;
import nl.maritoldenburger.bakehub.mappers.IngredientMapper;
import nl.maritoldenburger.bakehub.models.Ingredient;
import nl.maritoldenburger.bakehub.models.Recipe;
import nl.maritoldenburger.bakehub.repositories.IngredientRepository;
import nl.maritoldenburger.bakehub.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    public IngredientService(IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    public List<IngredientDto> getAllIngredients() {

        List<Ingredient> ingredients = ingredientRepository.findAll();
        List<IngredientDto> dtoIngredients = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            dtoIngredients.add(IngredientMapper.toDto(ingredient));
        }
        return dtoIngredients;
    }

    public IngredientDto getIngredientById(Long id) {

        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Ingredient " + id + " not found"));

        return IngredientMapper.toDto(ingredient);
    }

    public IngredientDto addIngredient(Long recipeId, IngredientInputDto ingredientInputDto) {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecordNotFoundException("Recipe " + recipeId + " not found"));

        Ingredient ingredient = IngredientMapper.toEntity(ingredientInputDto, recipe);

        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return IngredientMapper.toDto(savedIngredient);
    }

    public IngredientDto updateIngredient(Long id, IngredientInputDto ingredientInputDto) {

        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Ingredient " + id + " not found"));

        ingredient.setName(ingredientInputDto.name);
        ingredient.setQuantity(ingredientInputDto.quantity);
        ingredient.setUnit(ingredientInputDto.unit);

        Ingredient updatedIngredient = ingredientRepository.save(ingredient);

        return IngredientMapper.toDto(updatedIngredient);
    }

    public void deleteIngredient(Long id) {

        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Ingredient " + id + " not found"));
        ingredientRepository.delete(ingredient);
    }
}