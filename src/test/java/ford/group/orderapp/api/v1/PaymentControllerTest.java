package ford.group.orderapp.api.v1;
import ford.group.orderapp.dto.payment.PaymentDTO;
import ford.group.orderapp.dto.payment.PaymentToSaveDTO;
import ford.group.orderapp.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void testGet() throws Exception {
        // Arrange
        Long paymentId = 1L;
        PaymentDTO expectedPayment = new PaymentDTO(1L, null, 500.0, LocalDate.of(2024, 4, 1), "CASH");
        when(paymentService.findPaymentById(paymentId)).thenReturn(expectedPayment);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/{id}", paymentId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedPayment.id()));

        verify(paymentService, times(1)).findPaymentById(paymentId);
    }

    @Test
    public void testIndex() throws Exception {
        // Arrange
        List<PaymentDTO> expectedPayments = new ArrayList<>();
        when(paymentService.findAll()).thenReturn(expectedPayments);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(paymentService, times(1)).findAll();
    }

    @Test
    public void testGetByOrder() throws Exception {
        // Arrange
        Long orderId = 1L;
        List<PaymentDTO> expectedPayments = new ArrayList<>();
        when(paymentService.findByOrder(orderId)).thenReturn(expectedPayments);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/order/{orderId}", orderId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(paymentService, times(1)).findByOrder(orderId);
    }

    @Test
    public void testGetByRange() throws Exception {
        // Arrange
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);
        List<PaymentDTO> expectedPayments = new ArrayList<>();
        when(paymentService.findPaymentsByPayedAtBetween(startDate, endDate)).thenReturn(expectedPayments);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/date-range")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(paymentService, times(1)).findPaymentsByPayedAtBetween(startDate, endDate);
    }

    @Test
    public void testSave() throws Exception {
        // Arrange
        PaymentToSaveDTO paymentToSave = new PaymentToSaveDTO(null, 500.0, LocalDate.of(2024, 4, 1), "CASH");
        PaymentDTO expectedSavedPayment = new PaymentDTO(1L, null, 500.0, LocalDate.of(2024, 4, 1), "CASH");
        when(paymentService.savePayment(paymentToSave)).thenReturn(expectedSavedPayment);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/payments/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"totalPayment\":500, \"payedAt\":\"2024-04-01\", \"paymentMethod\":\"CASH\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedSavedPayment.id()));

        verify(paymentService, times(1)).savePayment(paymentToSave);
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        Long paymentId = 1L;
        PaymentToSaveDTO newPayment = new PaymentToSaveDTO(null, 500.0, LocalDate.of(2024, 4, 1), "CASH");
        PaymentDTO expectedUpdatedPayment = new PaymentDTO(1L, null, 500.0, LocalDate.of(2024, 4, 1), "CASH");
        when(paymentService.updatePayment(paymentId, newPayment)).thenReturn(expectedUpdatedPayment);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/payments/{id}", paymentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"totalPayment\": 500, \"payedAt\": \"2024-04-01\", \"paymentMethod\": \"CASH\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedUpdatedPayment.id()));

        verify(paymentService, times(1)).updatePayment(paymentId, newPayment);
    }

    @Test
    public void testDelete() throws Exception {
        // Arrange
        Long paymentId = 1L;

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/payments/{id}", paymentId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("removed"));

        verify(paymentService, times(1)).removePayment(paymentId);
    }
}
