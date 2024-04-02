package ford.group.orderapp.service;

import ford.group.orderapp.dto.order.OrderDTO;
import ford.group.orderapp.dto.order.OrderMapper;
import ford.group.orderapp.dto.order.OrderToSaveDTO;
import ford.group.orderapp.dto.ordereditem.OrderedItemDTO;
import ford.group.orderapp.dto.ordereditem.OrderedItemMapper;
import ford.group.orderapp.entities.Order;
import ford.group.orderapp.entities.OrderStatus;
import ford.group.orderapp.entities.OrderedItem;
import ford.group.orderapp.exception.ClientNotFoundException;
import ford.group.orderapp.exception.NotAbleToDeleteException;
import ford.group.orderapp.exception.OrderNotFoundException;
import ford.group.orderapp.repository.ClientRepository;
import ford.group.orderapp.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final OrderedItemMapper orderedItemMapper;

    public OrderServiceImpl(OrderMapper orderMapper, OrderRepository orderRepository, ClientRepository clientRepository, OrderedItemMapper orderedItemMapper) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.orderedItemMapper = orderedItemMapper;
    }


    @Override
    public OrderDTO saveOrder(OrderToSaveDTO orderDTO) {
        Order order = orderMapper.orderSaveDTOToOrder(orderDTO);
        Order orderSaved = orderRepository.save(order);
        return orderMapper.orderToOrderDTO(orderSaved);
    }

    @Override
    public OrderDTO updateOrder(Long id, OrderToSaveDTO orderDTO) {
        return orderRepository.findById(id).map(orderInDB -> {
            orderInDB.setClient(orderMapper.orderSaveDTOToOrder(orderDTO).getClient());
            orderInDB.setOrderedAt(orderDTO.orderedAt());
            orderInDB.setStatus(OrderStatus.valueOf(orderDTO.orderStatus()));
            Order orderSaved = orderRepository.save(orderInDB);
            return orderMapper.orderToOrderDTO(orderSaved);
        }).orElseThrow(() -> new OrderNotFoundException("Pedido no encontrado"));
    }

    @Override
    public OrderDTO findOrderById(Long id) throws OrderNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        return orderMapper.orderToOrderDTO(order);
    }

    @Override
    public void removeOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(NotAbleToDeleteException::new);
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDTO> findOrdersBetweenTwoDates(LocalDateTime date1, LocalDateTime date2) {
        List<Order> orders = orderRepository.findOrdersByOrderedAtBetween(date1,date2);
        if (orders.isEmpty())
            throw  new OrderNotFoundException("Pedidos no encontrados");
        return orders.stream().map(orderMapper::orderToOrderDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findOrdersByClientAndState(Long id, String status) {
        List<Order> orders = orderRepository.findOrdersByClientAndStatus(clientRepository.findById(id).orElseThrow(ClientNotFoundException::new),OrderStatus.valueOf(status));
        if (orders.isEmpty())
            throw new OrderNotFoundException("Pedidos no encontrados");
        return orders.stream().map(orderMapper::orderToOrderDTO).collect(Collectors.toList());
    }

    @Override
    public Map<OrderDTO, List<OrderedItemDTO>> findProductsByClient(Long clientId) {
        Map<Order, List<OrderedItem>> orders = orderRepository.findOrdersByClient(clientId);
        Map<OrderDTO, List<OrderedItemDTO>> ordersDTO = new HashMap<>();
        if (orders.isEmpty())
            throw new OrderNotFoundException("Pedidos no encontrados");
        for (Map.Entry<Order,List<OrderedItem>> entry : orders.entrySet()){
            Order order = entry.getKey();
            List<OrderedItem> orderedItems = entry.getValue();
            OrderDTO orderDTO = orderMapper.orderToOrderDTO(order);
            List<OrderedItemDTO> orderedItemDTOS = orderedItems.stream().map(orderedItemMapper::orderedItemToOrderedItemDTO).collect(Collectors.toList());
            ordersDTO.put(orderDTO, orderedItemDTOS);
        }
        return ordersDTO;
    }
}
