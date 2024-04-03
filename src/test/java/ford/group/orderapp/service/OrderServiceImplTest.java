package ford.group.orderapp.service;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.dto.order.OrderDTO;
import ford.group.orderapp.dto.order.OrderMapperImpl;
import ford.group.orderapp.dto.order.OrderToSaveDTO;
import ford.group.orderapp.entities.*;
import ford.group.orderapp.repository.ClientRepository;
import ford.group.orderapp.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    OrderRepository orderRepository;
    @Mock
    ClientRepository clientRepository;
    @Mock
    OrderServiceImpl orderService;
    Order order;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(new OrderMapperImpl(),orderRepository);

        order = Order.builder()
                .id(12L)
                .orderedAt(LocalDateTime.of(2024,5,15,0,0))
                .client(Client.builder().id(1L).build())
                .status(OrderStatus.PENDING)
                .orderedItems(List.of(OrderedItem.builder().id(5L).build()))
                .build();
    }

    @Test
    void saveOrder() {
        given(orderRepository.save(any())).willReturn(order);

        OrderToSaveDTO orderToSave = new OrderToSaveDTO(
                new ClientDTO(48L,"Test Client", "testclient@test.test", "Av Test"),
                LocalDate.of(2024,5,15),
                "PENDING"
        );

        OrderDTO savedOrder = orderService.saveOrder(orderToSave);

        assertThat(savedOrder.id()).isNotNull();
        assertThat(savedOrder.id()).isEqualTo(12);
        assertThat(savedOrder.status()).isEqualTo("PENDING");
        assertThat(savedOrder.orderedAt()).isEqualTo(LocalDate.of(2024,5,15).atStartOfDay());
    }

    @Test
    void updateOrder() {
        Long orderId = 12L;

        given(orderRepository.save(any())).willReturn(order);
        given(orderRepository.findById(any()))
                .willReturn(Optional.empty());

        given(orderRepository.findById(orderId))
                .willReturn(Optional.of(order));

        OrderToSaveDTO orderToSave = new OrderToSaveDTO(
                new ClientDTO(9L,"testname","testemail","testaddress"),
                LocalDate.of(2024,7,20),
                "SENT"
        );

        OrderDTO updatedOrder = orderService.updateOrder(orderId,orderToSave);

        assertThat(updatedOrder.status()).isEqualTo("SENT");
        assertThat(updatedOrder.orderedAt()).isEqualTo(LocalDate.of(2024,7,20).atStartOfDay());
    }

    @Test
    void findOrderById() {
        Long clientId = 12L;

        given(orderRepository.findById(any()))
                .willReturn(Optional.empty());

        given(orderRepository.findById(clientId))
                .willReturn(Optional.of(order));

        OrderDTO order = orderService.findOrderById(clientId);

        assertThat(order).isNotNull();
    }

    @Test
    void removeOrder() {
        Long orderId = 12L;

        willDoNothing().given(orderRepository).delete(any());
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        orderService.removeOrder(orderId);

        verify(orderRepository, times(1)).delete(any());
    }

    @Test
    void findOrdersBetweenTwoDates() {
        var from = LocalDate.of(2024, 4, 10).atStartOfDay();
        var to = LocalDate.of(2024, 5, 15).atStartOfDay();
        given(orderRepository.findOrdersByOrderedAtBetween(from, to)).willReturn(List.of(order));

        var orders = orderService.findOrdersBetweenTwoDates(from.toLocalDate(), to.toLocalDate());

        assertThat(orders).isNotEmpty();
    }

    @Test
    void findOrdersByClientAndState() {
        given(orderRepository.findOrdersByClientAndStatus(Client.builder().id(1L).build(), OrderStatus.PENDING))
                .willReturn(List.of(order));

        var orders = orderService.findOrdersByClientAndState(1L, "PENDING");

        assertThat(orders).isNotEmpty();
    }

    @Test
    void findProductsByClient() {
        given(orderRepository.findOrdersByClient(1L))
                .willReturn(List.of(order));

        var orders = orderService.findProductsByClient(1L);

        assertThat(orders).isNotEmpty();
        assertThat(orders.get(0).client().id()).isEqualTo(1L);
        assertThat(orders.get(0).orderedItems().get(0).id()).isEqualTo(5L);
    }
}