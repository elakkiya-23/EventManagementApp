package com.pdi.deloitte.EventManagement.Service;

import com.pdi.deloitte.EventManagement.Exception.ResourceNotFoundException;
import com.pdi.deloitte.EventManagement.model.Category;
import com.pdi.deloitte.EventManagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Create Category
    public Category createOrUpdateCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Update Category by ID
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catgeory not found with id " + id));

        category.setName(categoryDetails.getName());

        return categoryRepository.save(category);
    }
    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get category by id
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // Delete category by id
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
