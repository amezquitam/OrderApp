package ford.group.orderapp.repository;

import ford.group.orderapp.entities.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long> {
}
