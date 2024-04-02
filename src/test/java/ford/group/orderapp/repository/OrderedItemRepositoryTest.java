package ford.group.orderapp.repository;

import ford.group.orderapp.AbstractIntegrationDBTest;
import ford.group.orderapp.entities.Order;
import ford.group.orderapp.entities.OrderedItem;
import ford.group.orderapp.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderedItemRepositoryTest extends AbstractIntegrationDBTest {

    private final OrderedItemRepository orderedItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderedItemRepositoryTest(OrderedItemRepository orderedItemRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderedItemRepository = orderedItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @BeforeEach
    public void setUp() {
        orderedItemRepository.deleteAll();
        orderRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testFindOrderedItemsByOrder() {
        Order order = orderRepository.save(new Order());

        orderedItemRepository.save(new OrderedItem(1L, order, null, 20L, 50.0));
        orderedItemRepository.save(new OrderedItem(2L, order, null, 30L, 45.0));

        List<OrderedItem> orderedItems = orderedItemRepository.findOrderedItemsByOrder(order);

        assertFalse(orderedItems.isEmpty());
        assertEquals(2, orderedItems.size());
    }

    @Test
    public void testFindOrderedItemsByProduct() {
        Product product = productRepository.save(new Product());
        orderedItemRepository.save(new OrderedItem(1L, null, product, 20L, 50.0));
        orderedItemRepository.save(new OrderedItem(2L, null, product, 30L, 45.0));

        List<OrderedItem> orderedItems = orderedItemRepository.findOrderedItemsByProduct(product);

        assertFalse(orderedItems.isEmpty());
        assertEquals(2, orderedItems.size());
    }

    @Test
    public void testTotalSumOfSalesByProduct() {
        Product product = productRepository.save(new Product());

        orderedItemRepository.save(new OrderedItem(1L, null, product, 20L, 50.0));
        orderedItemRepository.save(new OrderedItem(2L, null, product, 30L, 45.0));

        Double totalSales = orderedItemRepository.totalSumOfSalesByProduct(product);

        assertEquals(20 * 50 + 30 * 45, totalSales);
    }
}
