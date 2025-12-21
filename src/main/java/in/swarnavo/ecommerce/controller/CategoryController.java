package in.swarnavo.ecommerce.controller;

import in.swarnavo.ecommerce.config.AppConstants;
import in.swarnavo.ecommerce.payload.CategoryDTO;
import in.swarnavo.ecommerce.payload.CategoryResponse;
import in.swarnavo.ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Tag(name = "Category APIs", description = "APIs for managing categories")
    @Operation(summary = "Fetch Categories", description = "API to fetch all categories")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All categories fetched successfully"),
            @ApiResponse(responseCode = "400", description = "No categories found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)

    })
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIRECTION) String sortOrder
    ) {
        return ResponseEntity.ok(categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder));
    }

    @Tag(name = "Category APIs", description = "APIs for managing categories")
    @Operation(summary = "Create Category", description = "API to create a new category")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category is created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)

    })
    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @Tag(name = "Category APIs", description = "APIs for managing categories")
    @Operation(summary = "Delete Category", description = "API to delete a category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)

    })
    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@Parameter(description = "ID of the category that you want to delete") @PathVariable Long categoryId) {
        CategoryDTO deleteCategory = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deleteCategory, HttpStatus.OK);
    }

    @Tag(name = "Category APIs", description = "APIs for managing categories")
    @Operation(summary = "Update Category", description = "API to update a category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)

    })
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Parameter(description = "ID of the category that you want to update") @PathVariable Long categoryId,@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.updateCategory(categoryId, categoryDTO);
        return new ResponseEntity<>(savedCategory, HttpStatus.OK);
    }
}
