package ford.group.orderapp.dto.order;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.dto.ordereditem.OrderedItemDTO;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record OrderDTO(
        Long id,
        ClientDTO client,
        LocalDateTime orderedAt,
        String status,
        @NotEmpty
        List<OrderedItemDTO> orderedItems

) {
    public List<OrderedItemDTO> orderedItems() {
        if (Objects.isNull(orderedItems)) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(orderedItems);
    }
}
