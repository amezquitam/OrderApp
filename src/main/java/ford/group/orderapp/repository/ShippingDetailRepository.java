package ford.group.orderapp.repository;

import ford.group.orderapp.entities.Order;
import ford.group.orderapp.entities.OrderStatus;
import ford.group.orderapp.entities.ShippingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShippingDetailRepository extends JpaRepository<ShippingDetail, Long> {
    ShippingDetail findShippingDetailByOrder(Order order);

    List<ShippingDetail> findShippingDetailsByDeliverer(String deliverer);

    @Query("SELECT sd FROM ShippingDetail sd WHERE sd.order.status = ?1")
    List<ShippingDetail> findShippingDetailsByOrderStatus(OrderStatus orderStatus);
}
