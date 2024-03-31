package ford.group.orderapp.dto.payment;

import ford.group.orderapp.entities.Payment;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentMapper {
    PaymentDTO paymentToPaymentDTO(Payment payment);
    Payment paymentDTOToPayment(PaymentDTO paymentDTO);
    Payment paymentSaveDTOToPaymente(PaymentToSaveDTO paymentToSaveDTO);
}