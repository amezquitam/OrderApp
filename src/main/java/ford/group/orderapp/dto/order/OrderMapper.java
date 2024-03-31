package ford.group.orderapp.dto.order;

import ford.group.orderapp.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO orderToOrderDTO(Order order);
    Order orderDTOToOrder(OrderDTO orderDTO);
    Order orderSaveDTOToOrder(OrderToSaveDTO orderToSaveDTO);
}
