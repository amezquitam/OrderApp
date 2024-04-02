package ford.group.orderapp.service;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.dto.client.ClientMapperImpl;
import ford.group.orderapp.dto.order.OrderDTO;
import ford.group.orderapp.dto.payment.PaymentDTO;
import ford.group.orderapp.dto.payment.PaymentToSaveDTO;
import ford.group.orderapp.dto.shippingdetail.ShippingDetailDTO;
import ford.group.orderapp.dto.shippingdetail.ShippingDetailMapperImpl;
import ford.group.orderapp.dto.shippingdetail.ShippingDetailToSaveDTO;
import ford.group.orderapp.entities.Client;
import ford.group.orderapp.entities.Order;
import ford.group.orderapp.entities.ShippingDetail;
import ford.group.orderapp.exception.ClientNotFoundException;
import ford.group.orderapp.exception.OrderNotFoundException;
import ford.group.orderapp.repository.OrderRepository;
import ford.group.orderapp.repository.ShippingDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShippingDetailServiceImplTest {
    ShippingDetailServiceImpl shippingDetailService;
    @Mock
    ShippingDetailRepository shippingDetailRepository;
    @Mock
    OrderRepository orderRepository;
    Order order = Order.builder().id(5L).build();

    ShippingDetail shippingDetail;
    @BeforeEach
    void setUp() {
        shippingDetailService = new ShippingDetailServiceImpl(shippingDetailRepository,new ShippingDetailMapperImpl(), orderRepository);

        shippingDetail = ShippingDetail.builder()
                .id(50L)
                .address("adress test")
                .order(order)
                .deliverer("deliverer test")
                .trackingNumber("123TEST")
                .build();
    }

    @Test
    void saveShippingDetail() {
        given(shippingDetailRepository.save(any())).willReturn(shippingDetail);

        ShippingDetailToSaveDTO shippingDetalToSave = new ShippingDetailToSaveDTO(
                new OrderDTO(10L,new ClientDTO(9L,"testname","testemail","testaddress"),
                        LocalDateTime.of(2024,12,12,16,32),"PENDING", Collections.emptyList()),
                "adress test",
                "deliverer test",
                "123TEST"
        );
        ShippingDetailDTO savedShippingDetal = shippingDetailService.saveShippingDetail(shippingDetalToSave);

        assertThat(savedShippingDetal.id()).isNotNull();
        assertThat(savedShippingDetal.id()).isEqualTo(50);
        assertThat(savedShippingDetal.address()).isEqualTo("adress test");
        assertThat(savedShippingDetal.deliverer()).isEqualTo("deliverer test");
        assertThat(savedShippingDetal.trackingNumber()).isEqualTo("123TEST");
    }

    @Test
    void updateShippingDetail() {
        Long shippingDetailId = 50L;

        given(shippingDetailRepository.save(any())).willReturn(shippingDetail);
        given(shippingDetailRepository.findById(any()))
                .willReturn(Optional.empty());

        given(shippingDetailRepository.findById(shippingDetailId))
                .willReturn(Optional.of(shippingDetail));

        ShippingDetailToSaveDTO shippingDetalToSave = new ShippingDetailToSaveDTO(
                new OrderDTO(10L,new ClientDTO(9L,"testname","testemail","testaddress"),
                        LocalDateTime.of(2024,12,12,16,32),"PENDING", Collections.emptyList()),
                "adress test updated",
                "deliverer test updated",
                "123TESTUPDATED"
        );

        ShippingDetailDTO updatedShippingDetail = shippingDetailService.updateShippingDetail(shippingDetailId,shippingDetalToSave);

        assertThat(updatedShippingDetail.address()).isEqualTo("adress test updated");
        assertThat(updatedShippingDetail.deliverer()).isEqualTo("deliverer test updated");
        assertThat(updatedShippingDetail.trackingNumber()).isEqualTo("123TESTUPDATED");
    }

    @Test
    void findShippingDetailById() {
        Long shippingDetailId = 50L;

        given(shippingDetailRepository.findById(any()))
                .willReturn(Optional.empty());

        given(shippingDetailRepository.findById(shippingDetailId))
                .willReturn(Optional.of(shippingDetail));

        ShippingDetailDTO shippingDetail = shippingDetailService.findShippingDetailById(shippingDetailId);

        assertThat(shippingDetail).isNotNull();
        assertThat(shippingDetail.id()).isEqualTo(50);
    }

    @Test
    void removeShippingDetail() {
        Long shippingDetailId = 50L;

        willDoNothing().given(shippingDetailRepository).delete(any());
        when(shippingDetailRepository.findById(shippingDetailId)).thenReturn(Optional.of(shippingDetail));

        shippingDetailService.removeShippingDetail(shippingDetailId);

        verify(shippingDetailRepository, times(1)).delete(any());
    }

    @Test
    void findShippingDetailByOrder() {
        /*Long orderId = 5L;
        given(shippingDetailRepository.findShippingDetailByOrder(order))
                .willReturn(shippingDetail);

        var shippingDetail = shippingDetailService.findShippingDetailByOrder(orderId);

        assertThat(shippingDetail).isNotNull();*/
    }

    @Test
    void findShippingDetailsByDeliverer() {
    }

    @Test
    void findShippingDetailsByOrderStatus() {
    }
}