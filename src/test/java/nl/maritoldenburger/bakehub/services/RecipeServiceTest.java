package nl.maritoldenburger.bakehub.services;

import nl.maritoldenburger.bakehub.dtos.ingredient.IngredientInputDto;
import nl.maritoldenburger.bakehub.dtos.recipe.RecipeDto;
import nl.maritoldenburger.bakehub.dtos.recipe.RecipeInputDto;
import nl.maritoldenburger.bakehub.exceptions.RecordNotFoundException;
import nl.maritoldenburger.bakehub.models.Category;
import nl.maritoldenburger.bakehub.models.Recipe;
import nl.maritoldenburger.bakehub.models.Review;
import nl.maritoldenburger.bakehub.models.User;
import nl.maritoldenburger.bakehub.repositories.CategoryRepository;
import nl.maritoldenburger.bakehub.repositories.RecipeRepository;
import nl.maritoldenburger.bakehub.repositories.ReviewRepository;
import nl.maritoldenburger.bakehub.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nl.maritoldenburger.bakehub.enums.Unit.GRAM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    IngredientService ingredientService;

    @InjectMocks
    RecipeService recipeService;

    Recipe recipe1 = new Recipe();
    Recipe recipe2 = new Recipe();

    Category category1 = new Category();

    User user1 = new User();

    Review review1 = new Review();
    Review review2 = new Review();
    Review review3 = new Review();

    RecipeInputDto recipeInputDto;

    @BeforeEach
    void setUp() {

        category1.setId(1L);
        category1.setName("taart");
        category1.setRecipes(new ArrayList<>());

        user1.setUsername("annie_ananas");

        recipe1.setId(1L);
        recipe1.setName("Speculoos cheesecake");
        recipe1.setDescription("Een romige no-bake cheesecake");
        recipe1.setInstructions("1. Verkruimel speculooskoekjes en meng met gesmolten boter.");
        recipe1.setServings(10);
        recipe1.setPreparationTime(30);
        recipe1.setRating(0.0);
        recipe1.setCategory(category1);
        recipe1.setReviews(new ArrayList<>());
        recipe1.setIngredients(new ArrayList<>());

        recipe2.setId(2L);
        recipe2.setName("Speculaascake");
        recipe2.setDescription("Een zachte kruidige cake");
        recipe2.setInstructions("1. Klop boter en suiker romig.");
        recipe2.setServings(18);
        recipe2.setPreparationTime(80);
        recipe2.setRating(5.0);
        recipe2.setCategory(category1);

        category1.getRecipes().add(recipe1);

        review1.setId(1L);
        review1.setRating(5);
        review1.setUser(user1);
        review1.setRecipe(recipe1);

        review2.setId(2L);
        review2.setRating(4);
        review2.setUser(user1);
        review2.setRecipe(recipe1);

        review3.setId(3L);
        review3.setRating(3);
        review3.setUser(user1);
        review3.setRecipe(recipe1);

        recipeInputDto = new RecipeInputDto();
        recipeInputDto.name = "De Test-taart";
        recipeInputDto.imageUrl = "test-taart.jpg";
        recipeInputDto.description = "Een heerlijke nieuwe taart. Natuurlijk met banaan.";
        recipeInputDto.instructions = "Maak de taart";
        recipeInputDto.servings = 8;
        recipeInputDto.preparationTime = 45;
        recipeInputDto.ingredients = new ArrayList<>();

        IngredientInputDto ingredient1 = new IngredientInputDto();
        ingredient1.name = "bloem";
        ingredient1.quantity = 250.0;
        ingredient1.unit = GRAM;
        recipeInputDto.ingredients.add(ingredient1);
    }

    @Test
    @DisplayName("getAllRecipes should return all existing recipes")
    void getAllRecipesShouldReturnAllRecipes() {

        List<Recipe> recipes = List.of(recipe1, recipe2);
        Mockito.when(recipeRepository.findAll()).thenReturn(recipes);

        List<RecipeDto> result = recipeService.getAllRecipes();

        assertEquals(2, result.size());
        assertEquals("Speculoos cheesecake", result.get(0).name);
        assertEquals("Speculaascake", result.get(1).name);
    }

    @Test
    @DisplayName("getRecipeById should return correct recipe")
    void getRecipeByIdShouldReturnCorrectRecipe() {

        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe1));

        RecipeDto result = recipeService.getRecipeById(1L);

        assertEquals(1L, result.id);
        assertEquals("Speculoos cheesecake", result.name);
        assertEquals("Een romige no-bake cheesecake", result.description);
        assertEquals(10, result.servings);
    }

    @Test
    @DisplayName("getRecipeById should throw exception when recipe is not found")
    void getRecipeByIdShouldThrowRecordNotFoundException() {

        Mockito.when(recipeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> recipeService.getRecipeById(99L));
    }

    @Test
    @DisplayName("getRecipesByCategory should return all recipes of existing category")
    void getRecipesByCategoryShouldReturnCorrectRecipes() {

        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));

        List<RecipeDto> result = recipeService.getRecipesByCategory(1L);

        assertEquals(1, result.size());
        assertEquals("Speculoos cheesecake", result.get(0).name);
    }

    @Test
    @DisplayName("getRecipesByCategory should throw exception when category not found")
    void getRecipesByCategoryShouldThrowRecordNotFoundException() {

        Mockito.when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> recipeService.getRecipesByCategory(99L));
    }

    @Test
    @DisplayName("searchRecipe should return recipes matching search query")
    void searchRecipeShouldReturnMatchingRecipes() {

        List<Recipe> foundRecipes = List.of(recipe1);
        Mockito.when(recipeRepository.findByNameContainingIgnoreCase("speculoos"))
                .thenReturn(foundRecipes);

        List<RecipeDto> result = recipeService.searchRecipe("speculoos");

        assertEquals(1, result.size());
        assertEquals("Speculoos cheesecake", result.get(0).name);
    }

    @Test
    @DisplayName("addRecipe should create and return new recipe")
    void addRecipeShouldCreateNewRecipe() {

        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(3L);
        savedRecipe.setName("De Test-taart");
        savedRecipe.setDescription("Een heerlijke nieuwe taart. Natuurlijk met banaan.");
        savedRecipe.setInstructions("Maak de taart");
        savedRecipe.setServings(8);
        savedRecipe.setPreparationTime(45);
        savedRecipe.setCategory(category1);
        savedRecipe.setReviews(new ArrayList<>());
        savedRecipe.setIngredients(new ArrayList<>());

        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        Mockito.when(recipeRepository.save(any(Recipe.class))).thenReturn(savedRecipe);
        Mockito.when(recipeRepository.findById(3L)).thenReturn(Optional.of(savedRecipe));

        RecipeDto result = recipeService.addRecipe(recipeInputDto, 1L);

        assertEquals("De Test-taart", result.name);
        assertEquals("Een heerlijke nieuwe taart. Natuurlijk met banaan.", result.description);
        verify(ingredientService).addIngredient(eq(3L), any(IngredientInputDto.class));
    }

    @Test
    @DisplayName("updateRecipe should update existing recipe")
    void updateRecipeShouldUpdateExistingRecipe() {

        Recipe updatedRecipe = new Recipe();
        updatedRecipe.setId(1L);
        updatedRecipe.setName("De Test-taart");
        updatedRecipe.setImageUrl("test-taart.jpg");
        updatedRecipe.setDescription("Een heerlijke nieuwe taart. Natuurlijk met banaan.");
        updatedRecipe.setInstructions("Maak de taart");
        updatedRecipe.setServings(8);
        updatedRecipe.setPreparationTime(45);
        updatedRecipe.setCategory(category1);
        updatedRecipe.setReviews(new ArrayList<>());

        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe1));
        Mockito.when(recipeRepository.save(any(Recipe.class))).thenReturn(updatedRecipe);

        RecipeDto result = recipeService.updateRecipe(1L, recipeInputDto);

        assertEquals(1L, result.id);
        assertEquals("De Test-taart", result.name);
        assertEquals("Een heerlijke nieuwe taart. Natuurlijk met banaan.", result.description);
        assertEquals(8, result.servings);
        assertEquals(45, result.preparationTime);
    }

    @Test
    @DisplayName("deleteRecipe should delete existing recipe")
    void deleteRecipeShouldDeleteRecipe() {

        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe1));

        recipeService.deleteRecipe(1L);

        verify(recipeRepository).delete(recipe1);
    }

    @Test
    @DisplayName("deleteRecipe should throw exception when recipe not found")
    void deleteRecipeShouldThrowRecordNotFoundException() {

        Mockito.when(recipeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> recipeService.deleteRecipe(99L));
    }

    @Test
    @DisplayName("updateRecipeRating should calculate average rating correctly")
    void updateRecipeRatingShouldCalculateAverageRating() {

        recipe1.setReviews(List.of(review1, review2, review3));
        Mockito.when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe1);

        recipeService.updateRecipeRating(recipe1);

        assertEquals(4.0, recipe1.getRating());
        verify(recipeRepository).save(recipe1);
    }

    @Test
    @DisplayName("updateRecipeRating should set rating to 0 when there are no reviews")
    void updateRecipeRatingShouldSetRatingToZeroWhenNoReviewsExist() {

        recipe1.setReviews(new ArrayList<>());
        Mockito.when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe1);

        recipeService.updateRecipeRating(recipe1);

        assertEquals(0.0, recipe1.getRating());
        verify(recipeRepository).save(recipe1);
    }
}