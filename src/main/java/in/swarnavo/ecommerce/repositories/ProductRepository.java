package in.swarnavo.ecommerce.repositories;

import in.swarnavo.ecommerce.model.Category;
import in.swarnavo.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryOrderByPriceAsc(Category category);
}
