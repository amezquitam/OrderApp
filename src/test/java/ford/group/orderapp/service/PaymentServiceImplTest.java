package ford.group.orderapp.service;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.dto.client.ClientMapperImpl;
import ford.group.orderapp.dto.client.ClientToSaveDTO;
import ford.group.orderapp.dto.order.OrderDTO;
import ford.group.orderapp.dto.payment.PaymentDTO;
import ford.group.orderapp.dto.payment.PaymentMapperImpl;
import ford.group.orderapp.dto.payment.PaymentToSaveDTO;
import ford.group.orderapp.entities.*;
import ford.group.orderapp.repository.OrderRepository;
import ford.group.orderapp.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cglib.core.Local;

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
class PaymentServiceImplTest {
    @Mock
    PaymentRepository paymentRepository;
    @Mock
    OrderRepository orderRepository;
    PaymentServiceImpl paymentService;
    Payment payment;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentServiceImpl(new PaymentMapperImpl(), paymentRepository, orderRepository);

        payment = Payment.builder()
                .id(48L)
                .order(Order.builder().build())
                .paymentMethod(PaymentMethod.CASH)
                .payedAt(LocalDate.of(2024,6,16))
                .totalPayment(2000.0)
                .build();
    }

    @Test
    void savePayment() {
        given(paymentRepository.save(any())).willReturn(payment);

        PaymentToSaveDTO paymentToSave = new PaymentToSaveDTO(
                new OrderDTO(10L,new ClientDTO(9L,"testname","testemail","testaddress"),
                                    LocalDateTime.of(2024,12,12,16,32),"PENDING",Collections.emptyList()),
                2000.0,
                LocalDate.of(2024,6,16)
                ,"CASH");

        PaymentDTO savedPayment = paymentService.savePayment(paymentToSave);

        assertThat(savedPayment.id()).isNotNull();
        assertThat(savedPayment.id()).isEqualTo(48L);
        assertThat(savedPayment.totalPayment()).isEqualTo(2000.0);
        assertThat(savedPayment.paymentMethod()).isEqualTo("CASH");
    }

    @Test
    void updatePayment() {
        Long paymentId = 48L;

        given(paymentRepository.save(any())).willReturn(payment);
        given(paymentRepository.findById(any()))
                .willReturn(Optional.empty());

        given(paymentRepository.findById(paymentId))
                .willReturn(Optional.of(payment));

        PaymentToSaveDTO paymentToSave = new PaymentToSaveDTO(
                new OrderDTO(10L,new ClientDTO(9L,"testname","testemail","testaddress"),
                        LocalDateTime.of(2024,12,12,16,32),"PENDING",Collections.emptyList()),
                2500.0,
                LocalDate.of(2024,7,20),
                "PAYPAL"
        );

        PaymentDTO updatedPayment = paymentService.updatePayment(paymentId,paymentToSave);

        assertThat(updatedPayment.paymentMethod()).isEqualTo("PAYPAL");
        assertThat(updatedPayment.totalPayment()).isEqualTo(2500.0);
    }

    @Test
    void findPaymentById() {
        Long paymentId = 48L;

        given(paymentRepository.findById(any()))
                .willReturn(Optional.empty());

        given(paymentRepository.findById(paymentId))
                .willReturn(Optional.of(payment));

        PaymentDTO payment = paymentService.findPaymentById(paymentId);

        assertThat(payment).isNotNull();
        assertThat(payment.id()).isEqualTo(48);
    }

    @Test
    void removePayment() {
        Long paymentId = 48L;

        willDoNothing().given(paymentRepository).delete(any());
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));

        paymentService.removePayment(paymentId);

        verify(paymentRepository, times(1)).delete(any());
    }

    @Test
    void findPaymentsByPayedAtBetween() {

    }

    @Test
    void findPaymentsByOrderAndPaymentMethod() {
    }
}