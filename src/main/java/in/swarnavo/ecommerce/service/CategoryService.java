package in.swarnavo.ecommerce.service;

import in.swarnavo.ecommerce.model.Category;
import in.swarnavo.ecommerce.payload.CategoryDTO;
import in.swarnavo.ecommerce.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);
}
