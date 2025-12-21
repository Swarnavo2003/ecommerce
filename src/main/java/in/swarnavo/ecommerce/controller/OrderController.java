package in.swarnavo.ecommerce.controller;

import in.swarnavo.ecommerce.payload.OrderDTO;
import in.swarnavo.ecommerce.payload.OrderRequestDTO;
import in.swarnavo.ecommerce.service.OrderService;
import in.swarnavo.ecommerce.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthUtil authUtil;

    @Tag(name = "Order APIs", description = "APIs for managing orders")
    @Operation(summary = "Order Products", description = "API to place orders")
    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> orderProducts(
            @PathVariable String paymentMethod,
            @RequestBody OrderRequestDTO orderRequestDTO
    ) {
        String emailId = authUtil.loggedInEmail();
        OrderDTO order = orderService.placeOrder(
                emailId,
                orderRequestDTO.getAddressId(),
                paymentMethod,
                orderRequestDTO.getPgName(),
                orderRequestDTO.getPgPaymentId(),
                orderRequestDTO.getPgStatus(),
                orderRequestDTO.getPgResponseMessage()
        );
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
