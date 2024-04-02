package ford.group.orderapp.repository;

import ford.group.orderapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByNameContains(String term);

    /**
     * Products in stock
     * @param zero: Always pass zero
     */
    List<Product> findProductsByStockGreaterThan(Integer zero);

    List<Product> findProductByPriceLessThanAndStockLessThan(Double maxPrice, Integer maxStock);
}
