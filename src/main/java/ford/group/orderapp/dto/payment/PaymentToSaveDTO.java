package ford.group.orderapp.dto.payment;

import ford.group.orderapp.dto.order.OrderDTO;

import java.time.LocalDate;

public record PaymentToSaveDTO(OrderDTO order,
                               Double totalPayment,
                               LocalDate payedAt,
                               String paymentMethod) {
}
