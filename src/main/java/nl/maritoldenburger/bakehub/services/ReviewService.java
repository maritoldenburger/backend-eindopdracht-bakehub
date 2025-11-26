package nl.maritoldenburger.bakehub.services;

import nl.maritoldenburger.bakehub.dtos.review.ReviewDto;
import nl.maritoldenburger.bakehub.dtos.review.ReviewInputDto;
import nl.maritoldenburger.bakehub.exceptions.RecordNotFoundException;
import nl.maritoldenburger.bakehub.mappers.ReviewMapper;
import nl.maritoldenburger.bakehub.models.Recipe;
import nl.maritoldenburger.bakehub.models.Review;
import nl.maritoldenburger.bakehub.models.User;
import nl.maritoldenburger.bakehub.repositories.RecipeRepository;
import nl.maritoldenburger.bakehub.repositories.ReviewRepository;
import nl.maritoldenburger.bakehub.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<ReviewDto> getReviewsByRecipe(Long recipeId) {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecordNotFoundException("Recipe " + recipeId + " not found"));

        List<ReviewDto> dtoReviews = new ArrayList<>();

        for (Review review : recipe.getReviews()) {
            dtoReviews.add(ReviewMapper.toDto(review));
        }
        return dtoReviews;
    }

    public ReviewDto addReview(Long userId, Long recipeId, ReviewInputDto reviewInputDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User " + userId + " not found"));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecordNotFoundException("Recipe " + recipeId + " not found"));

        Review review = ReviewMapper.toEntity(reviewInputDto, user, recipe);
        Review savedReview = reviewRepository.save(review);

        recipeService.updateRecipeRating(recipe);

        return ReviewMapper.toDto(savedReview);
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Review " + id + " not found"));

        Recipe recipe = review.getRecipe();

        reviewRepository.delete(review);
        recipeService.updateRecipeRating(recipe);
    }
}