package ford.group.orderapp.dto.payment;

import ford.group.orderapp.entities.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDTO paymentToPaymentDTO(Payment payment);
    Payment paymentDTOToPayment(PaymentDTO paymentDTO);
    Payment paymentSaveDTOToPayment(PaymentToSaveDTO paymentToSaveDTO);
}
