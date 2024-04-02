package ford.group.orderapp.repository;

import ford.group.orderapp.AbstractIntegrationDBTest;
import ford.group.orderapp.entities.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ProductRepositoryTest extends AbstractIntegrationDBTest {

    private final ProductRepository productRepository;

    @Autowired
    public ProductRepositoryTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @BeforeEach
    public void setUp() {
        productRepository.deleteAll();
    }

    @Test
    public void testFindProductsByNameContains() {
        Product product1 = Product.builder().name("Laptop Lenovo").build();
        Product product2 = Product.builder().name("Mouse Logitech").build();
        Product product3 = Product.builder().name("Keyboard Logitech").build();
        productRepository.saveAll(List.of(product1, product2, product3));

        List<Product> foundProducts = productRepository.findProductsByNameContains("Logitech");
        assertEquals(2, foundProducts.size());
    }

    @Test
    public void testFindProductsByStockGreaterThan() {
        Product product1 = Product.builder().stock(10).build();
        Product product2 = Product.builder().stock(0).build();
        Product product3 = Product.builder().stock(20).build();
        productRepository.saveAll(List.of(product1, product2, product3));

        List<Product> foundProducts = productRepository.findProductsByStockGreaterThan(0);
        assertEquals(2, foundProducts.size());
    }

    @Test
    public void testFindProductByPriceLessThanAndStockLessThan() {
        Product product1 = Product.builder().price(100.0).stock(5).build();
        Product product2 = Product.builder().price(50.0).stock(15).build();
        Product product3 = Product.builder().price(200.0).stock(10).build();

        productRepository.saveAll(List.of(product1, product2, product3));

        List<Product> foundProducts = productRepository.findProductByPriceLessThanAndStockLessThan(150.0, 10);
        assertEquals(1, foundProducts.size());
    }

    @Test
    public void testCRUDOperations() {
        // Test for save
        Product product = new Product();
        productRepository.save(product);
        assertNotNull(product.getId());

        // Test find by id
        Product foundProduct = productRepository.findById(product.getId()).orElse(null);
        assertNotNull(foundProduct);
        assertEquals(product.getId(), foundProduct.getId());

        // Test for update
        foundProduct.setName("Updated Product");
        productRepository.save(foundProduct);
        Product updatedProduct = productRepository.findById(product.getId()).orElse(null);
        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());

        // Test para delete
        productRepository.deleteById(product.getId());
        assertFalse(productRepository.existsById(product.getId()));
    }
}
