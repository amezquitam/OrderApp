package ford.group.orderapp.dto.order;

import ford.group.orderapp.dto.client.ClientDTO;

import java.time.LocalDate;

public record OrderToSaveDTO(ClientDTO client,
                             LocalDate orderedAt,
                             String status) {
}
