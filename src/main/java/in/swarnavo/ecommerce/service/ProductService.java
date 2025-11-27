package in.swarnavo.ecommerce.service;

import in.swarnavo.ecommerce.model.Product;
import in.swarnavo.ecommerce.payload.ProductDTO;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);
}
