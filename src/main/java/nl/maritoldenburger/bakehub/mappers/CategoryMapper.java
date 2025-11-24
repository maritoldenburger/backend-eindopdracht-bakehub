package nl.maritoldenburger.bakehub.mappers;

import nl.maritoldenburger.bakehub.dtos.category.CategoryDto;
import nl.maritoldenburger.bakehub.dtos.category.CategoryInputDto;
import nl.maritoldenburger.bakehub.models.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryInputDto categoryInputDto) {
        Category category = new Category();

        category.setName(categoryInputDto.name);
        category.setDescription(categoryInputDto.description);
        category.setImageUrl(categoryInputDto.imageUrl);

        return category;
    }

    public static CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.id = category.getId();
        categoryDto.name = category.getName();
        categoryDto.description = category.getDescription();
        categoryDto.imageUrl = category.getImageUrl();

        return categoryDto;
    }
}
