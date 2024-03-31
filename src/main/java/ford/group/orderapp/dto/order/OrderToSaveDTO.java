package ford.group.orderapp.dto.order;

import ford.group.orderapp.dto.client.ClientDTO;

import java.time.LocalDateTime;

public record OrderToSaveDTO(ClientDTO client,
                             LocalDateTime orderedAt,
                             String orderStatus) {
}
