package ford.group.orderapp.dto.order;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.dto.ordereditem.OrderedItemDTO;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record OrderDTO(
        Long id,
        ClientDTO client,
        LocalDateTime orderedAt,
        String orderStatus,
        List<OrderedItemDTO> orderedItems


) {
    public List<OrderedItemDTO> orderedItems(){
        return Collections.unmodifiableList(orderedItems);
    }
}
