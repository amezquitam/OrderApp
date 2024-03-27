package ford.group.orderapp.repository;

import ford.group.orderapp.AbstractIntegrationDBTest;
import ford.group.orderapp.entities.Client;
import ford.group.orderapp.entities.Order;
import ford.group.orderapp.entities.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ClientRepository clientRepository;
    List<Order> orders;
    List<Client> clients;
    Order order;
    Client client;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();

        clients = List.of(
                Client.builder()
                        .name("Juan Lozano")
                        .email("juanlozano@email.test")
                        .address("K15C12AV").build(),
                Client.builder()
                        .name("Louis Guerra")
                        .email("lguerra@email.test")
                        .address("K10C74HF").build()
        );

        clients = clientRepository.saveAll(clients);

        orders = List.of(
                Order.builder()
                        .client(clients.get(0))
                        .orderedAt(LocalDateTime.of(2024, 3, 20, 0, 0, 0))
                        .status(OrderStatus.SENT).build(),
                Order.builder()
                        .client(clients.get(0))
                        .orderedAt(LocalDateTime.of(2024, 3, 25, 0, 0, 0))
                        .status(OrderStatus.PENDING).build(),
                Order.builder()
                        .client(clients.get(1))
                        .orderedAt(LocalDateTime.of(2024, 3, 14, 0, 0, 0))
                        .status(OrderStatus.DELIVERED).build()
        );

        client = Client.builder()
                .name("Maria Moreno")
                .email("mmoreno@email.test")
                .address("K10C74HG").build();

        order = Order.builder()
                .orderedAt(LocalDateTime.of(2024, 3, 11, 0, 0, 0))
                .status(OrderStatus.DELIVERED).build();
    }

    @Test
    void createAnOrder() {
        // Given a client and an order, and tables empty
        orderRepository.deleteAll();
        clientRepository.deleteAll();

        // When save order in database
        Client savedClient = clientRepository.save(client);

        order.setClient(savedClient);

        Order savedOrder = orderRepository.save(order);

        // Then id is not null
        assertThat(savedOrder.getId()).isNotNull();

        var clientsInDB = clientRepository.findAll();
        var ordersInDB = orderRepository.findAll();

        assertThat(clientsInDB).hasSize(1);
        assertThat(ordersInDB).hasSize(1);
    }

    @Test
    void findOrderById() {
        // given some orders in DB
        var ordersInDB = orderRepository.saveAll(orders);

        var order = ordersInDB.get(0);

        // when find an order
        var foundOrderOptional = orderRepository.findById(order.getId());

        // then it exists and are identical
        assertThat(foundOrderOptional).isPresent();

        var foundOrder = foundOrderOptional.get();

        assertThat(foundOrder.getClient()).isEqualTo(order.getClient());
        assertThat(foundOrder.getStatus()).isEqualTo(order.getStatus());
        assertThat(foundOrder.getOrderedAt()).isEqualTo(order.getOrderedAt());
    }

    @Test
    void whenSaveAnOrderWithExistentIdThenItIsOverwritten() {
        // given some orders in DB
        var ordersInDB = orderRepository.saveAll(orders);
        
        var actualOrder = ordersInDB.get(0);
        
        // then modify one, and save
        actualOrder.setStatus(OrderStatus.SENT);
        
        var orderSaved = orderRepository.save(actualOrder);
        
        // then, both objects has the same id
        assertThat(actualOrder.getId()).isEqualTo(orderSaved.getId());
    }

    @Test
    void deleteOrder() {
        // given some orders in DB
        var ordersInDB = orderRepository.saveAll(orders);
        var initialOrderCount = orderRepository.count();

        // removing some item from database
        orderRepository.delete(ordersInDB.get(0));

        // then order count is reduced by one
        var finalOrderCount = orderRepository.count();

        assertThat(initialOrderCount).isGreaterThan(finalOrderCount);
        assertThat(initialOrderCount - 1).isEqualTo(finalOrderCount);
    }

    @Test
    void findOrdersByOrderedAtBetween() {
    }

    @Test
    void findOrdersByClientAndStatus() {
    }

    @Test
    void findOrdersByClient() {
    }
}