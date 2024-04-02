package ford.group.orderapp.repository;

import ford.group.orderapp.AbstractIntegrationDBTest;
import ford.group.orderapp.entities.Order;
import ford.group.orderapp.entities.OrderStatus;
import ford.group.orderapp.entities.ShippingDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ShippingDetailRepositoryTest extends AbstractIntegrationDBTest {

    private final ShippingDetailRepository shippingDetailRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public ShippingDetailRepositoryTest(ShippingDetailRepository shippingDetailRepository, OrderRepository orderRepository) {
        this.shippingDetailRepository = shippingDetailRepository;
        this.orderRepository = orderRepository;
    }

    @BeforeEach
    public void setUp() {
        shippingDetailRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    public void testFindShippingDetailByOrder() {
        Order order = orderRepository.save(new Order());
        ShippingDetail shippingDetail = ShippingDetail.builder().order(order).deliverer("Delivery Company").build();
        shippingDetailRepository.save(shippingDetail);

        ShippingDetail foundShippingDetail = shippingDetailRepository.findShippingDetailByOrder(order);
        assertNotNull(foundShippingDetail);
        assertEquals(shippingDetail.getId(), foundShippingDetail.getId());
    }

    @Test
    public void testFindShippingDetailsByDeliverer() {
        Order order = orderRepository.save(new Order());
        ShippingDetail shippingDetail1 = ShippingDetail.builder().order(order).deliverer("Delivery A").build();
        ShippingDetail shippingDetail2 = ShippingDetail.builder().order(order).deliverer("Delivery B").build();
        shippingDetailRepository.saveAll(List.of(shippingDetail1, shippingDetail2));

        List<ShippingDetail> foundShippingDetails = shippingDetailRepository.findShippingDetailsByDeliverer("Delivery A");
        assertEquals(1, foundShippingDetails.size());
    }

    @Test
    public void testFindShippingDetailsByOrderStatus() {
        Order order = orderRepository.save(Order.builder().status(OrderStatus.DELIVERED).build());

        ShippingDetail shippingDetail = ShippingDetail.builder().order(order).deliverer("Delivery B").build();
        shippingDetailRepository.save(shippingDetail);

        List<ShippingDetail> foundShippingDetails = shippingDetailRepository.findShippingDetailsByOrderStatus(OrderStatus.DELIVERED);
        assertEquals(1, foundShippingDetails.size());
    }

    @Test
    public void testCRUDOperations() {
        // Test for save
        ShippingDetail shippingDetail = new ShippingDetail();
        shippingDetailRepository.save(shippingDetail);
        assertNotNull(shippingDetail.getId());

        // Test find by id
        ShippingDetail foundShippingDetail = shippingDetailRepository.findById(shippingDetail.getId()).orElse(null);
        assertNotNull(foundShippingDetail);
        assertEquals(shippingDetail.getId(), foundShippingDetail.getId());

        // Test for update
        foundShippingDetail.setDeliverer("Updated Delivery Company");
        shippingDetailRepository.save(foundShippingDetail);
        ShippingDetail updatedShippingDetail = shippingDetailRepository.findById(shippingDetail.getId()).orElse(null);
        assertNotNull(updatedShippingDetail);
        assertEquals("Updated Delivery Company", updatedShippingDetail.getDeliverer());

        // Test for delete
        shippingDetailRepository.deleteById(shippingDetail.getId());
        assertFalse(shippingDetailRepository.existsById(shippingDetail.getId()));
    }
}
