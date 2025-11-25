package nl.maritoldenburger.bakehub.controllers;

import nl.maritoldenburger.bakehub.dtos.recipe.RecipeDto;
import nl.maritoldenburger.bakehub.dtos.recipe.RecipeInputDto;
import nl.maritoldenburger.bakehub.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<RecipeDto> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long id) {
        RecipeDto recipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<RecipeDto>> getAllRecipesByCategory(@PathVariable Long categoryId) {
        List<RecipeDto> recipes = recipeService.getRecipesByCategory(categoryId);
        return ResponseEntity.ok(recipes);
    }

    @PostMapping("/category/{categoryId}")
    public ResponseEntity<RecipeDto> addRecipe(@RequestBody RecipeInputDto recipeInputDto, @PathVariable Long categoryId) {
        RecipeDto savedRecipe = recipeService.addRecipe(recipeInputDto, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, @RequestBody RecipeInputDto recipeInputDto) {
        RecipeDto updatedRecipe = recipeService.updateRecipe(id, recipeInputDto);
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    //todo - search functie

    //todo - filter functie (rating + prep time?)
}