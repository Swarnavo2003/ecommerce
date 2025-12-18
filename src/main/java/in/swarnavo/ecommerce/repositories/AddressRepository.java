package in.swarnavo.ecommerce.repositories;

import in.swarnavo.ecommerce.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
