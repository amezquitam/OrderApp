package ford.group.orderapp.service;

import ford.group.orderapp.dto.order.OrderDTO;
import ford.group.orderapp.dto.order.OrderToSaveDTO;
import ford.group.orderapp.dto.ordereditem.OrderedItemDTO;
import ford.group.orderapp.entities.OrderStatus;
import ford.group.orderapp.exception.OrderNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderService {
    OrderDTO saveOrder(OrderToSaveDTO orderDTO);
    OrderDTO updateOrder(Long id, OrderToSaveDTO orderDTO);
    OrderDTO findOrderById(Long id) throws OrderNotFoundException;
    void removeOrder(Long id);
    List<OrderDTO> findOrdersBetweenTwoDates(LocalDateTime date1, LocalDateTime date2);
    List<OrderDTO> findOrdersByClientAndState(Long id, OrderStatus status);
    Map<OrderDTO, List<OrderedItemDTO>> findProductsByClient(Long id); // en veremos

}
