package ford.group.orderapp.dto;

import ford.group.orderapp.dto.order.OrderDTO;

public record ShippingDetailDTO(
        Long id,
        OrderDTO order,
        String address,
        String deliverer,
        String trackingNumber
) {
}
