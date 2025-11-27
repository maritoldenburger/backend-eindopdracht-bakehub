package nl.maritoldenburger.bakehub.controllers;

import nl.maritoldenburger.bakehub.dtos.review.ReviewDto;
import nl.maritoldenburger.bakehub.dtos.review.ReviewInputDto;
import nl.maritoldenburger.bakehub.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/recipes/{recipeId}/reviews")
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

    @PostMapping
    public ResponseEntity<ReviewDto> addReview(@PathVariable Long recipeId, @RequestBody ReviewInputDto reviewInputDto) {
        ReviewDto savedReview = reviewService.addReview(reviewInputDto.userId, recipeId, reviewInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id, Principal principal) {
        reviewService.deleteReview(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}