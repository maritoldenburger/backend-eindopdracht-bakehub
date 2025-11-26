package nl.maritoldenburger.bakehub.services;

import nl.maritoldenburger.bakehub.dtos.category.CategoryDto;
import nl.maritoldenburger.bakehub.dtos.category.CategoryInputDto;
import nl.maritoldenburger.bakehub.exceptions.RecordNotFoundException;
import nl.maritoldenburger.bakehub.mappers.CategoryMapper;
import nl.maritoldenburger.bakehub.models.Category;
import nl.maritoldenburger.bakehub.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> dtoCategories = new ArrayList<>();

        for (Category category : categories) {
            dtoCategories.add(CategoryMapper.toDto(category));
        }
        return dtoCategories;
    }

    public CategoryDto getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category " + id + " not found"));

        return CategoryMapper.toDto(category);
    }

    public CategoryDto addCategory(CategoryInputDto categoryInputDto) {

        Category category = CategoryMapper.toEntity(categoryInputDto);
        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toDto(savedCategory);
    }

    public CategoryDto updateCategory(Long id, CategoryInputDto categoryInputDto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category " + id + " not found"));

        category.setName(categoryInputDto.name);
        category.setDescription(categoryInputDto.description);
        category.setImageUrl(categoryInputDto.imageUrl);

        Category updatedCategory = categoryRepository.save(category);

        return CategoryMapper.toDto(updatedCategory);
    }

    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category " + id + " not found"));
        categoryRepository.delete(category);
    }
}