package ford.group.orderapp.repository;

import ford.group.orderapp.entities.Client;
import ford.group.orderapp.entities.Order;
import ford.group.orderapp.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrdersByOrderedAtBetween(LocalDateTime from, LocalDateTime to);

    List<Order> findOrdersByClientAndStatus(Client client, OrderStatus status);
    // @Query("SELECT sd FROM Order sd join fetch OrderedItem pd ON pd.id = sd.id")
    @Query("SELECT o FROM Order o join fetch o.orderedItems where o.client.id = ?1")
    List<Order> findOrdersByClient(Long clientId);
}
