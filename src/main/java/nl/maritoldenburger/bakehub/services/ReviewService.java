package nl.maritoldenburger.bakehub.services;

import nl.maritoldenburger.bakehub.dtos.review.ReviewDto;
import nl.maritoldenburger.bakehub.dtos.review.ReviewInputDto;
import nl.maritoldenburger.bakehub.exceptions.BadRequestException;
import nl.maritoldenburger.bakehub.exceptions.RecordNotFoundException;
import nl.maritoldenburger.bakehub.mappers.ReviewMapper;
import nl.maritoldenburger.bakehub.models.Recipe;
import nl.maritoldenburger.bakehub.models.Review;
import nl.maritoldenburger.bakehub.models.User;
import nl.maritoldenburger.bakehub.repositories.RecipeRepository;
import nl.maritoldenburger.bakehub.repositories.ReviewRepository;
import nl.maritoldenburger.bakehub.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;

    @Value("${my.upload_location}")
    private String uploadLocation;

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

    public ReviewDto addReview(String username, Long recipeId, ReviewInputDto reviewInputDto, MultipartFile image) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("User " + username + " not found"));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecordNotFoundException("Recipe " + recipeId + " not found"));

        Review review = ReviewMapper.toEntity(reviewInputDto, user, recipe);

        if (image != null && !image.isEmpty()) {
            try {
                Files.createDirectories(Path.of(uploadLocation));
                String filename = image.getOriginalFilename();
                Path path = Path.of(uploadLocation + "/" + filename);
                Files.copy(image.getInputStream(), path);
                review.setImageUrl("/uploads/" + filename);
            } catch (IOException exception) {
                throw new RuntimeException("Unable to save image", exception);
            }
        }

        Review savedReview = reviewRepository.save(review);

        recipeService.updateRecipeRating(recipe);

        return ReviewMapper.toDto(savedReview);
    }

    public void deleteReview(Long id, String username) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Review " + id + " not found"));

        if (!review.getUser().getUsername().equals(username)) {
            throw new BadRequestException("You can only delete your own reviews");
        }

        Recipe recipe = review.getRecipe();

        reviewRepository.delete(review);
        recipeService.updateRecipeRating(recipe);
    }
}