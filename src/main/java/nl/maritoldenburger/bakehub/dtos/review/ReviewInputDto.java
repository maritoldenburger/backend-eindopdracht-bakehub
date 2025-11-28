package nl.maritoldenburger.bakehub.dtos.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class ReviewInputDto {

    @Min(1)
    @Max(5)
    public Integer rating;

    @Size(min = 5, max = 500)
    public String comment;

    public String imageUrl;
}
