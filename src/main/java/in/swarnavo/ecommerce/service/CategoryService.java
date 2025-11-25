package in.swarnavo.ecommerce.service;

import in.swarnavo.ecommerce.model.Category;
import in.swarnavo.ecommerce.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long categoryId);
    Category updateCategory(Long categoryId, Category category);
}
