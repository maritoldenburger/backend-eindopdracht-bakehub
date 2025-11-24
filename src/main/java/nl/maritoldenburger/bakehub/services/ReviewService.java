package nl.maritoldenburger.bakehub.services;

import nl.maritoldenburger.bakehub.exceptions.RecordNotFoundException;
import nl.maritoldenburger.bakehub.models.Recipe;
import nl.maritoldenburger.bakehub.models.Review;
import nl.maritoldenburger.bakehub.models.User;
import nl.maritoldenburger.bakehub.repositories.RecipeRepository;
import nl.maritoldenburger.bakehub.repositories.ReviewRepository;
import nl.maritoldenburger.bakehub.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, RecipeRepository recipeRepository, RecipeService recipeService) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.recipeService = recipeService;
    }

    public List<Review> getReviewsByRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecordNotFoundException("Recipe " + recipeId + " not found"));
        return recipe.getReviews();
    }

    public Review addReview(Long userId, Long recipeId, Review data) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User " + userId + " not found"));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecordNotFoundException("Recipe " + recipeId + " not found"));

        data.setUser(user);
        data.setRecipe(recipe);

        Review savedReview = reviewRepository.save(data);

        recipeService.updateRecipeRating(recipe);

        return savedReview;
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Review " + id + " not found"));

        Recipe recipe = review.getRecipe();

        reviewRepository.delete(review);
        recipeService.updateRecipeRating(recipe);
    }
}