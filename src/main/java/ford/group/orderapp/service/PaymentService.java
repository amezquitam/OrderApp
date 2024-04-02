package ford.group.orderapp.service;

import ford.group.orderapp.dto.payment.PaymentDTO;
import ford.group.orderapp.dto.payment.PaymentToSaveDTO;
import ford.group.orderapp.exception.PaymentNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface PaymentService {
    PaymentDTO savePayment(PaymentToSaveDTO paymentDTO);
    PaymentDTO updatePayment(Long id, PaymentToSaveDTO paymentDTO);
    PaymentDTO findPaymentById(Long id) throws PaymentNotFoundException;
    void removePayment(Long id);
    List<PaymentDTO> findPaymentsByPayedAtBetween(LocalDate from, LocalDate to);
    List<PaymentDTO> findPaymentsByOrderAndPaymentMethod(Long orderId, String paymentMethod);
    List<PaymentDTO> findByOrder(Long id);
    List<PaymentDTO> findAll();
}
