package nl.maritoldenburger.bakehub.mappers;

import nl.maritoldenburger.bakehub.dtos.review.ReviewDto;
import nl.maritoldenburger.bakehub.dtos.review.ReviewInputDto;
import nl.maritoldenburger.bakehub.models.Recipe;
import nl.maritoldenburger.bakehub.models.Review;
import nl.maritoldenburger.bakehub.models.User;

public class ReviewMapper {

    public static Review toEntity(ReviewInputDto reviewInputDto, User user, Recipe recipe) {
        Review review = new Review();

        review.setRating(reviewInputDto.rating);
        review.setComment(reviewInputDto.comment);
        review.setImageUrl(reviewInputDto.imageUrl);
        review.setUser(user);
        review.setRecipe(recipe);

        return review;
    }

    public static ReviewDto toDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();

        reviewDto.id = review.getId();
        reviewDto.rating = review.getRating();
        reviewDto.comment = review.getComment();
        reviewDto.imageUrl = review.getImageUrl();
        reviewDto.username = review.getUser().getUsername();
        reviewDto.recipeId = review.getRecipe().getId();

        return reviewDto;
    }
}
