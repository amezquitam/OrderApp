package ford.group.orderapp.repository;

import ford.group.orderapp.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
