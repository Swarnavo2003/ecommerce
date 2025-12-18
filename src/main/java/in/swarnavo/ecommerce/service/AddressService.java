package in.swarnavo.ecommerce.service;

import in.swarnavo.ecommerce.model.User;
import in.swarnavo.ecommerce.payload.AddressDTO;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);
}
