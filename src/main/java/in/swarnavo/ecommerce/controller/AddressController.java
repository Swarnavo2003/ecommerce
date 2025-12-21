package in.swarnavo.ecommerce.controller;

import in.swarnavo.ecommerce.model.User;
import in.swarnavo.ecommerce.payload.AddressDTO;
import in.swarnavo.ecommerce.service.AddressService;
import in.swarnavo.ecommerce.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AuthUtil authUtil;

    @Tag(name = "Address APIs", description = "APIs for managing addresses")
    @Operation(summary = "Create Address", description = "API to create address")
    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        User user = authUtil.loggedInUser();
        AddressDTO savedAddressDto = addressService.createAddress(addressDTO, user);
        return new ResponseEntity<>(savedAddressDto, HttpStatus.CREATED);
    }

    @Tag(name = "Address APIs", description = "APIs for managing addresses")
    @Operation(summary = "Get User Addresses", description = "API to get user addresses")
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAddresses() {
        List<AddressDTO> addressDTOList = addressService.getAddresses();
        return ResponseEntity.ok(addressDTOList);
    }

    @Tag(name = "Address APIs", description = "APIs for managing addresses")
    @Operation(summary = "Get Address By Id", description = "API to get address by id")
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) {
        AddressDTO addressDTO = addressService.getAddressById(addressId);
        return ResponseEntity.ok(addressDTO);
    }

    @Tag(name = "Address APIs", description = "APIs for managing addresses")
    @Operation(summary = "Get User Address", description = "API to get user address")
    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>> getUserAddresses() {
        User user = authUtil.loggedInUser();
        List<AddressDTO> userAddresses = addressService.getUserAddresses(user);
        return ResponseEntity.ok(userAddresses);
    }

    @Tag(name = "Address APIs", description = "APIs for managing addresses")
    @Operation(summary = "Update Address", description = "API to update address")
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId, @RequestBody AddressDTO addressDTO) {
        AddressDTO updatedAddress = addressService.updateAddress(addressId, addressDTO);
        return ResponseEntity.ok(updatedAddress);
    }

    @Tag(name = "Address APIs", description = "APIs for managing addresses")
    @Operation(summary = "Delete Address", description = "API to delete address")
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) {
        String status = addressService.deleteAddress(addressId);
        return ResponseEntity.ok(status);
    }
}
