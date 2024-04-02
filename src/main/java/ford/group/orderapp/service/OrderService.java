package ford.group.orderapp.service;

import ford.group.orderapp.dto.order.OrderDTO;
import ford.group.orderapp.dto.order.OrderToSaveDTO;
import ford.group.orderapp.dto.ordereditem.OrderedItemDTO;
import ford.group.orderapp.exception.OrderNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderService {
    OrderDTO saveOrder(OrderToSaveDTO orderDTO);
    OrderDTO updateOrder(Long id, OrderToSaveDTO orderDTO);
    OrderDTO findOrderById(Long id) throws OrderNotFoundException;
    void removeOrder(Long id);
    List<OrderDTO> findOrdersBetweenTwoDates(LocalDate startDate, LocalDate endDate);
    List<OrderDTO> findOrdersByClientAndState(Long id, String status);
    Map<OrderDTO, List<OrderedItemDTO>> findProductsByClient(Long clientId);

    List<OrderDTO> findAll();

    List<OrderDTO> findOrdersByClient(Long id);
}
