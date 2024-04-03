package ford.group.orderapp.service;

import ford.group.orderapp.dto.order.OrderDTO;
import ford.group.orderapp.dto.order.OrderMapper;
import ford.group.orderapp.dto.order.OrderToSaveDTO;
import ford.group.orderapp.entities.Client;
import ford.group.orderapp.entities.Order;
import ford.group.orderapp.entities.OrderStatus;
import ford.group.orderapp.exception.NotAbleToDeleteException;
import ford.group.orderapp.exception.OrderNotFoundException;
import ford.group.orderapp.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderMapper orderMapper, OrderRepository orderRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
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
            orderInDB.setOrderedAt(orderDTO.orderedAt().atStartOfDay());
            orderInDB.setStatus(OrderStatus.valueOf(orderDTO.status()));
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
    public List<OrderDTO> findOrdersBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
        List<Order> orders = orderRepository.findOrdersByOrderedAtBetween(startDate.atStartOfDay(), endDate.atStartOfDay());
        return orders.stream().map(orderMapper::orderToOrderDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findOrdersByClientAndState(Long id, String status) {
        Client client = Client.builder().id(id).build();
        List<Order> orders = orderRepository.findOrdersByClientAndStatus(client, OrderStatus.valueOf(status));
        return orders.stream().map(orderMapper::orderToOrderDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findProductsByClient(Long clientId) {
        List<Order> orders = orderRepository.findOrdersByClient(clientId);

        return orders.stream()
                .map(orderMapper::orderToOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::orderToOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findOrdersByClient(Long id) {
        return orderRepository.findOrdersByClient(id).stream()
                .map(orderMapper::orderToOrderDTO)
                .collect(Collectors.toList());
    }
}
