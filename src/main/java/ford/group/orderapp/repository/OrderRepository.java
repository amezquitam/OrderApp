package ford.group.orderapp.repository;

import ford.group.orderapp.entities.Client;
import ford.group.orderapp.entities.Order;
import ford.group.orderapp.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrdersByOrderedAtBetween(LocalDateTime from, LocalDateTime to);

    List<Order> findOrdersByClientAndStatus(Client client, OrderStatus status);

    List<Order> findOrdersByClient(Client client);
}
