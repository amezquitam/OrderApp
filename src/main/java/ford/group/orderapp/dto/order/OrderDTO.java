package ford.group.orderapp.dto.order;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.dto.ordereditem.OrderedItemDTO;
import ford.group.orderapp.entities.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(
        Long id,
        ClientDTO client,
        LocalDateTime orderedAt,
        OrderStatus orderStatus,
        List<OrderedItemDTO> orderedItems
) {
}
