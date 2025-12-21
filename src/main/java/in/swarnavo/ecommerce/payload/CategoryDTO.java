package in.swarnavo.ecommerce.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    @Schema(description = "Category ID", example = "101")
    private Long categoryId;

    @Schema(description = "Category name for category you want to create", name = "iPhone 17")
    private String categoryName;
}
