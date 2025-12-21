package in.swarnavo.ecommerce.controller;

import in.swarnavo.ecommerce.model.Cart;
import in.swarnavo.ecommerce.payload.CartDTO;
import in.swarnavo.ecommerce.repositories.CartRepository;
import in.swarnavo.ecommerce.service.CartService;
import in.swarnavo.ecommerce.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private CartRepository cartRepository;

    @Tag(name = "Cart APIs", description = "APIs for managing carts")
    @Operation(summary = "Add Product To Cart", description = "API to add products to cart")
    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(
            @Parameter(description = "ID of the product that you want to add to cart") @PathVariable Long productId,
            @Parameter(description = "Quantity of product that you want to add to cart") @PathVariable Integer quantity)
    {
        CartDTO cartDTO = cartService.addProductToCart(productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    @Tag(name = "Cart APIs", description = "APIs for managing carts")
    @Operation(summary = "Get All Carts", description = "API to get all carts")
    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getCarts() {
        List<CartDTO> cartDTOS = cartService.getAllCarts();
        return new ResponseEntity<>(cartDTOS, HttpStatus.FOUND);
    }

    @Tag(name = "Cart APIs", description = "APIs for managing carts")
    @Operation(summary = "Get User Carts", description = "API to get user cart")
    @GetMapping("/carts/user/cart")
    public ResponseEntity<CartDTO> getCartById() {
        String emailId = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(emailId);
        Long cartId = cart.getCartId();
        CartDTO cartDTO = cartService.getCart(emailId, cartId);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @Tag(name = "Cart APIs", description = "APIs for managing carts")
    @Operation(summary = "Update Products In Cart", description = "API to update products in cart")
    @PutMapping("/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCartProduct(
            @PathVariable Long productId,
            @PathVariable String operation
    ) {
        CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,
                operation.equalsIgnoreCase("delete") ? -1 : 1);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @Tag(name = "Cart APIs", description = "APIs for managing carts")
    @Operation(summary = "Delete Products From Cart", description = "API to delete products from carts")
    @DeleteMapping("/carts/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(
            @PathVariable Long cartId,
            @PathVariable Long productId
    ) {
        String status = cartService.deleteProductFromCart(cartId, productId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
