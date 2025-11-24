package nl.maritoldenburger.bakehub.services;

import nl.maritoldenburger.bakehub.exceptions.RecordNotFoundException;
import nl.maritoldenburger.bakehub.models.Category;
import nl.maritoldenburger.bakehub.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category " + id + " not found"));
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category " + id + " not found"));

        category.setName(updatedCategory.getName());
        category.setDescription(updatedCategory.getDescription());
        category.setImageUrl(updatedCategory.getImageUrl());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category " + id + " not found"));
        categoryRepository.delete(category);
    }
}