package in.swarnavo.ecommerce.service;

import in.swarnavo.ecommerce.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);
}
