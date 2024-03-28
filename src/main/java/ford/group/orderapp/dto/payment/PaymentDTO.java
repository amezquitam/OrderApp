package ford.group.orderapp.dto.payment;

import ford.group.orderapp.dto.order.OrderDTO;
import ford.group.orderapp.entities.PaymentMethod;

import java.time.LocalDate;

public record PaymentDTO(
        Long id,
        OrderDTO order,
        Double totalPayment,
        LocalDate payedAt,
        PaymentMethod paymentMethod
) {
}
