package ford.group.orderapp.dto.shippingdetail;

import ford.group.orderapp.dto.order.OrderDTO;

public record ShippingDetailToSaveDTO(OrderDTO order,
                                      String address,
                                      String deliverer,
                                      String trackingNumber) {
}
