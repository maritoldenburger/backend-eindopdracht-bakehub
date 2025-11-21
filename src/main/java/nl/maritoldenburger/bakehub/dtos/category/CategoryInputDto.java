package nl.maritoldenburger.bakehub.dtos.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryInputDto {

    @NotBlank(message = "Name is required")
    @Size(min=3, max=128)
    public String name;

    @NotBlank(message = "Description is required")
    @Size(min=5, max=300)
    public String description;

    @NotBlank(message = "Image is required")
    public String imageUrl;
}
