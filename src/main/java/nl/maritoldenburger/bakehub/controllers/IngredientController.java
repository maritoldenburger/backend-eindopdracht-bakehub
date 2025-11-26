package nl.maritoldenburger.bakehub.controllers;

import nl.maritoldenburger.bakehub.dtos.ingredient.IngredientDto;
import nl.maritoldenburger.bakehub.dtos.ingredient.IngredientInputDto;
import nl.maritoldenburger.bakehub.services.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<IngredientDto>> getAllIngredients() {
        List<IngredientDto> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDto> getIngredientById(@PathVariable Long id) {
        IngredientDto ingredient = ingredientService.getIngredientById(id);
        return ResponseEntity.ok(ingredient);
    }

    @PostMapping("/recipes/{recipeId}")
    public ResponseEntity<IngredientDto> addIngredient(@PathVariable Long recipeId, @RequestBody IngredientInputDto ingredientInputDto) {
        IngredientDto savedIngredient = ingredientService.addIngredient(recipeId, ingredientInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIngredient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientDto> updateIngredient(@PathVariable Long id, @RequestBody IngredientInputDto ingredientInputDto) {
        IngredientDto updatedIngredient = ingredientService.updateIngredient(id, ingredientInputDto);
        return ResponseEntity.ok(updatedIngredient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}