package ford.group.orderapp.service;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.dto.order.OrderDTO;
import ford.group.orderapp.dto.order.OrderMapperImpl;
import ford.group.orderapp.dto.order.OrderToSaveDTO;
import ford.group.orderapp.dto.ordereditem.OrderedItemMapperImpl;
import ford.group.orderapp.dto.payment.PaymentDTO;
import ford.group.orderapp.dto.payment.PaymentToSaveDTO;
import ford.group.orderapp.entities.Client;
import ford.group.orderapp.entities.Order;
import ford.group.orderapp.entities.OrderStatus;
import ford.group.orderapp.repository.ClientRepository;
import ford.group.orderapp.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
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
        orderService = new OrderServiceImpl(new OrderMapperImpl(),orderRepository,clientRepository,new OrderedItemMapperImpl());

        order = Order.builder()
                .id(12L)
                .orderedAt(LocalDateTime.of(2024,5,15,0,0))
                .client(Client.builder().build())
                .status(OrderStatus.PENDING)
                .orderedItems(Collections.emptyList())
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
    }

    @Test
    void findOrdersByClientAndState() {
    }

    @Test
    void findProductsByClient() {
    }
}