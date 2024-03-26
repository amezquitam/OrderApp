package ford.group.orderapp.repository;

import ford.group.orderapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findProductsByNameContains(String term);

    /**
     * Products in stock
     * @param zero: Always pass zero
     */
    public List<Product> findProductsByStockGreaterThan(Integer zero);

    public List<Product> findProductByPriceLessThanAndStockLessThan(Double maxPrice, Integer maxStock);
}
