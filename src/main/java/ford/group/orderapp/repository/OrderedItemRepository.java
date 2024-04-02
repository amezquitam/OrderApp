package ford.group.orderapp.repository;

import ford.group.orderapp.entities.Order;
import ford.group.orderapp.entities.OrderedItem;
import ford.group.orderapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long> {
    List<OrderedItem> findOrderedItemsByOrder(Order order);

    List<OrderedItem> findOrderedItemsByProduct(Product product);

    @Query("SELECT SUM(oi.requestedAmount * oi.unitPrice) AS totalSales FROM OrderedItem oi " +
            "WHERE oi.product = ?1 GROUP BY oi.product")
    Double totalSumOfSalesByProduct(Product product);
}
