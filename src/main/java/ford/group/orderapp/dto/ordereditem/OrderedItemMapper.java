package ford.group.orderapp.dto.ordereditem;

import ford.group.orderapp.entities.OrderedItem;
import org.mapstruct.Mapper;

@Mapper
public interface OrderedItemMapper {
    OrderedItemDTO orderedItemToOrderedItemDTO(OrderedItem orderedItem);
    OrderedItem orderedItemDTOToOrderedItem(OrderedItemDTO orderedItemDTO);
}
