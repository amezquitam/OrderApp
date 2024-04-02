package ford.group.orderapp.dto.order;

import ford.group.orderapp.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "order.status", target = "orderStatus")
    OrderDTO orderToOrderDTO(Order order);
    @Mapping(source = "orderDTO.orderStatus", target = "status")
    Order orderDTOToOrder(OrderDTO orderDTO);
    @Mapping(source = "orderToSaveDTO.orderStatus", target = "status")
    Order orderSaveDTOToOrder(OrderToSaveDTO orderToSaveDTO);
}
