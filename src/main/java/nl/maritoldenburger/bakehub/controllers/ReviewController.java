package nl.maritoldenburger.bakehub.controllers;

import nl.maritoldenburger.bakehub.dtos.review.ReviewDto;
import nl.maritoldenburger.bakehub.dtos.review.ReviewInputDto;
import nl.maritoldenburger.bakehub.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getReviewsByRecipe(@PathVariable Long recipeId) {
        List<ReviewDto> reviews = reviewService.getReviewsByRecipe(recipeId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ReviewDto> addReview(@PathVariable Long userId, @RequestBody Long recipeId, @RequestBody ReviewInputDto reviewInputDto) {
        ReviewDto reviewDto = reviewService.addReview(userId, recipeId, reviewInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}