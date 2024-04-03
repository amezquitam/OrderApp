package ford.group.orderapp.service;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.dto.order.OrderDTO;
import ford.group.orderapp.dto.shippingdetail.ShippingDetailDTO;
import ford.group.orderapp.dto.shippingdetail.ShippingDetailMapperImpl;
import ford.group.orderapp.dto.shippingdetail.ShippingDetailToSaveDTO;
import ford.group.orderapp.entities.Order;
import ford.group.orderapp.entities.OrderStatus;
import ford.group.orderapp.entities.ShippingDetail;
import ford.group.orderapp.repository.OrderRepository;
import ford.group.orderapp.repository.ShippingDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
class ShippingDetailServiceImplTest {
    ShippingDetailServiceImpl shippingDetailService;
    @Mock
    ShippingDetailRepository shippingDetailRepository;
    @Mock
    OrderRepository orderRepository;
    Order order = Order.builder()
            .id(5L)
            .status(OrderStatus.SENT).build();
    ShippingDetail shippingDetail;

    @BeforeEach
    void setUp() {
        shippingDetailService = new ShippingDetailServiceImpl(shippingDetailRepository, new ShippingDetailMapperImpl(), orderRepository);

        shippingDetail = ShippingDetail.builder()
                .id(50L)
                .address("address test")
                .order(order)
                .deliverer("deliverer test")
                .trackingNumber("123TEST")
                .build();
    }

    @Test
    void saveShippingDetail() {
        given(shippingDetailRepository.save(any())).willReturn(shippingDetail);

        ShippingDetailToSaveDTO shippingDetailToSave = new ShippingDetailToSaveDTO(
                new OrderDTO(10L, new ClientDTO(9L, "testname", "testemail", "testaddress"),
                        LocalDateTime.of(2024, 12, 12, 16, 32), "PENDING", Collections.emptyList()),
                "address test",
                "deliverer test",
                "123TEST"
        );
        ShippingDetailDTO savedShippingDetail = shippingDetailService.saveShippingDetail(shippingDetailToSave);

        assertThat(savedShippingDetail.id()).isNotNull();
        assertThat(savedShippingDetail.id()).isEqualTo(50);
        assertThat(savedShippingDetail.address()).isEqualTo("address test");
        assertThat(savedShippingDetail.deliverer()).isEqualTo("deliverer test");
        assertThat(savedShippingDetail.trackingNumber()).isEqualTo("123TEST");
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
                new OrderDTO(10L, new ClientDTO(9L, "testname", "testemail", "testaddress"),
                        LocalDateTime.of(2024, 12, 12, 16, 32), "PENDING", Collections.emptyList()),
                "adress test updated",
                "deliverer test updated",
                "123TESTUPDATED"
        );

        ShippingDetailDTO updatedShippingDetail = shippingDetailService.updateShippingDetail(shippingDetailId, shippingDetalToSave);

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
        when(shippingDetailRepository.findShippingDetailByOrder(order)).thenReturn(shippingDetail);

        ShippingDetailDTO foundedShippingDetail = shippingDetailService.findShippingDetailByOrder(order.getId());

        assertThat(foundedShippingDetail).isNotNull();
    }

    @Test
    void findShippingDetailsByDeliverer() {
        String deliver = "deliverer test";
        given(shippingDetailRepository.findShippingDetailsByDeliverer(deliver)).willReturn(List.of(shippingDetail));
        var shippingDetails = shippingDetailService.findShippingDetailsByDeliverer(deliver);
        assertThat(shippingDetails).isNotEmpty();
    }

    @Test
    void findShippingDetailsByOrderStatus() {
        OrderStatus status = OrderStatus.SENT;
        given(shippingDetailRepository.findShippingDetailsByOrderStatus(status)).willReturn(List.of(shippingDetail));
        var shippingDetails = shippingDetailService.findShippingDetailsByOrderStatus(status.name());
        assertThat(shippingDetails).isNotEmpty();
    }
}