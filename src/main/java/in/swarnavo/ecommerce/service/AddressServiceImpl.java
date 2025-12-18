package in.swarnavo.ecommerce.service;

import in.swarnavo.ecommerce.model.Address;
import in.swarnavo.ecommerce.model.User;
import in.swarnavo.ecommerce.payload.AddressDTO;
import in.swarnavo.ecommerce.repositories.AddressRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    @Transactional
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {
        Address address = modelMapper.map(addressDTO, Address.class);
        List<Address> addressList = user.getAddresses();
        addressList.add(address);

        user.setAddresses(addressList);

        address.setUser(user);

        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddresses() {
        List<Address> addressList = addressRepository.findAll();
        List<AddressDTO> addressDTOList = addressList
                .stream()
                .map(address ->
                        modelMapper.map(address, AddressDTO.class)
                )
                .toList();
        return addressDTOList;
    }
}
