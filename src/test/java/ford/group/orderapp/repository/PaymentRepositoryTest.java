package ford.group.orderapp.repository;

import ford.group.orderapp.AbstractIntegrationDBTest;
import ford.group.orderapp.entities.Order;
import ford.group.orderapp.entities.Payment;
import ford.group.orderapp.entities.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class PaymentRepositoryTest extends AbstractIntegrationDBTest {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentRepositoryTest(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @BeforeEach
    public void setUp() {
        paymentRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    public void testFindPaymentsByPayedAtBetween() {
        paymentRepository.save(
                Payment.builder().
                        payedAt(LocalDate.now().minusDays(1))
                        .build()
        );

        paymentRepository.save(
                Payment.builder().
                        payedAt(LocalDate.now().minusDays(0))
                        .build()
        );

        paymentRepository.save(
                Payment.builder().
                        payedAt(LocalDate.now().minusDays(3))
                        .build()
        );

        List<Payment> payments = paymentRepository.findPaymentsByPayedAtBetween(LocalDate.now().minusDays(1), LocalDate.now());
        assertEquals(2, payments.size());
    }

    @Test
    public void testFindPaymentsByOrderAndPaymentMethod() {
        Order order = orderRepository.save(new Order());
        PaymentMethod paymentMethod = PaymentMethod.DAVIPLATA;

        paymentRepository.save(
                Payment.builder()
                        .order(order)
                        .paymentMethod(paymentMethod)
                        .totalPayment(223.0)
                        .build()
        );

        paymentRepository.save(
                Payment.builder()
                        .order(order)
                        .paymentMethod(paymentMethod)
                        .build()
        );

        paymentRepository.save(
                Payment.builder()
                        .order(order)
                        .paymentMethod(PaymentMethod.NEQUI)
                        .build()
        );

        List<Payment> payments = paymentRepository.findPaymentsByOrderAndPaymentMethod(order, paymentMethod);
        assertEquals(2, payments.size());
    }

    @Test
    public void testFindPaymentsByOrder() {
        Order order = orderRepository.save(new Order());
        Order order2 = orderRepository.save(new Order());

        Payment payment1 = new Payment();
        payment1.setOrder(order);
        paymentRepository.save(payment1);

        Payment payment2 = new Payment();
        payment2.setOrder(order);
        paymentRepository.save(payment2);

        Payment payment3 = new Payment();
        payment3.setOrder(order2);
        paymentRepository.save(payment3);

        List<Payment> payments = paymentRepository.findPaymentsByOrder(order.getId());
        assertEquals(2, payments.size());
    }

    @Test
    public void testCRUDOperations() {
        // Test for save
        Payment payment = new Payment();
        paymentRepository.save(payment);
        assertNotNull(payment.getId());

        // Test para find by id
        Payment foundPayment = paymentRepository.findById(payment.getId()).orElse(null);
        assertNotNull(foundPayment);
        assertEquals(payment.getId(), foundPayment.getId());

        // Test for update
        foundPayment.setTotalPayment(100.0);
        paymentRepository.save(foundPayment);
        Payment updatedPayment = paymentRepository.findById(payment.getId()).orElse(null);
        assertNotNull(updatedPayment);
        assertEquals(100.0, updatedPayment.getTotalPayment());

        // Test para delete
        paymentRepository.deleteById(payment.getId());
        assertFalse(paymentRepository.existsById(payment.getId()));
    }
}
