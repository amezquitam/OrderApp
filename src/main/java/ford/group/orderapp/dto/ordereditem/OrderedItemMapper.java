package ford.group.orderapp.dto.ordereditem;

import ford.group.orderapp.dto.order.OrderToSaveDTO;
import ford.group.orderapp.entities.OrderedItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderedItemMapper {
    OrderedItemDTO orderedItemToOrderedItemDTO(OrderedItem orderedItem);
    OrderedItem orderedItemDTOToOrderedItem(OrderedItemDTO orderedItemDTO);
    OrderedItem orderedItemSaveDTOToOrderedItem(OrderToSaveDTO orderToSaveDTO);
}
