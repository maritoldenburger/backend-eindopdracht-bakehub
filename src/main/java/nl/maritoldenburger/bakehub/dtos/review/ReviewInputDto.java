package nl.maritoldenburger.bakehub.dtos.review;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReviewInputDto {

    @NotNull
    public Integer rating;

    @Size(min = 5, max = 500)
    public String comment;

    public String imageUrl;

    public Long userId;

    public Long recipeId;
}
