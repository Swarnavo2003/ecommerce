package in.swarnavo.ecommerce.service;

import in.swarnavo.ecommerce.payload.ProductDTO;
import in.swarnavo.ecommerce.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);
}
