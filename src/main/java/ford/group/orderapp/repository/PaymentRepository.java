package ford.group.orderapp.repository;

import ford.group.orderapp.entities.Order;
import ford.group.orderapp.entities.Payment;
import ford.group.orderapp.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findPaymentsByPayedAtBetween(LocalDate from, LocalDate to);

    List<Payment> findPaymentsByOrderAndPaymentMethod(Order order, PaymentMethod paymentMethod);

    @Query("select p from Payment p where p.order.id = ?1")
    List<Payment> findPaymentsByOrder(Long id);

}
