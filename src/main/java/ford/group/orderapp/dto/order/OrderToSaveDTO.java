package ford.group.orderapp.dto.order;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.entities.OrderStatus;

import java.time.LocalDateTime;

public record OrderToSaveDTO(ClientDTO client,
                             LocalDateTime orderedAt,
                             OrderStatus orderStatus) {
}
