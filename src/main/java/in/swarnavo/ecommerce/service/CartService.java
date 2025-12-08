package in.swarnavo.ecommerce.service;

import in.swarnavo.ecommerce.payload.CartDTO;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);
}
