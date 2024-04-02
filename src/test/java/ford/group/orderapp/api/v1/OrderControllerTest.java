package ford.group.orderapp.api.v1;

import ford.group.orderapp.dto.order.OrderDTO;
import ford.group.orderapp.dto.order.OrderToSaveDTO;
import ford.group.orderapp.service.OrderService;
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
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void testGet() throws Exception {
        // Arrange
        Long orderId = 1L;
        OrderDTO expectedOrder = new OrderDTO(1L, null, LocalDate.of(2024, 4, 1).atStartOfDay(), "PENDING", List.of());
        when(orderService.findOrderById(orderId)).thenReturn(expectedOrder);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/{id}", orderId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedOrder.id()));

        verify(orderService, times(1)).findOrderById(orderId);
    }

    @Test
    public void testIndex() throws Exception {
        // Arrange
        List<OrderDTO> expectedOrders = new ArrayList<>();
        when(orderService.findAll()).thenReturn(expectedOrders);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(orderService, times(1)).findAll();
    }

    @Test
    public void testGetByCustomer() throws Exception {
        // Arrange
        Long customerId = 1L;
        List<OrderDTO> expectedOrders = new ArrayList<>();
        when(orderService.findOrdersByClient(customerId)).thenReturn(expectedOrders);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/customer/{customerId}", customerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(orderService, times(1)).findOrdersByClient(customerId);
    }

    @Test
    public void testGetByRange() throws Exception {
        // Arrange
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);
        List<OrderDTO> expectedOrders = new ArrayList<>();
        when(orderService.findOrdersBetweenTwoDates(startDate, endDate)).thenReturn(expectedOrders);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/date-range")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(orderService, times(1)).findOrdersBetweenTwoDates(startDate, endDate);
    }

    @Test
    public void testSave() throws Exception {
        // Arrange
        OrderToSaveDTO orderToSave =  new OrderToSaveDTO(null, LocalDate.of(2024, 4, 1), "PENDING");
        OrderDTO expectedSavedOrder =  new OrderDTO(1L, null, LocalDate.of(2024, 4, 1).atStartOfDay(), "PENDING", List.of());
        when(orderService.saveOrder(orderToSave)).thenReturn(expectedSavedOrder);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderedAt\": \"2024-04-01\", \"orderStatus\": \"PENDING\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedSavedOrder.id()));

        verify(orderService, times(1)).saveOrder(orderToSave);
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        Long orderId = 1L;
        OrderToSaveDTO newOrder = new OrderToSaveDTO(null, LocalDate.of(2024, 4, 1), "PENDING");
        OrderDTO expectedUpdatedOrder = new OrderDTO(1L, null, LocalDate.of(2024, 4, 1).atStartOfDay(), "PENDING", List.of());
        when(orderService.updateOrder(orderId, newOrder)).thenReturn(expectedUpdatedOrder);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderedAt\": \"2024-04-01\", \"orderStatus\": \"PENDING\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedUpdatedOrder.id()));

        verify(orderService, times(1)).updateOrder(orderId, newOrder);
    }

    @Test
    public void testDelete() throws Exception {
        // Arrange
        Long orderId = 1L;

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/orders/{id}", orderId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("removed"));

        verify(orderService, times(1)).removeOrder(orderId);
    }
}
