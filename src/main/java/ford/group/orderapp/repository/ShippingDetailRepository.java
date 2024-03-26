package ford.group.orderapp.repository;

import ford.group.orderapp.entities.ShippingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingDetailRepository extends JpaRepository<ShippingDetail, Long> {
}
