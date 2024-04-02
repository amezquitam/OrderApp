package ford.group.orderapp.api.v1;

import ford.group.orderapp.dto.ordereditem.OrderedItemDTO;
import ford.group.orderapp.dto.ordereditem.OrderedItemToSaveDTO;
import ford.group.orderapp.service.OrderedItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(OrderedItemController.class)
public class OrderedItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderedItemService orderedItemService;

    @Test
    public void testGet() throws Exception {
        // Arrange
        Long orderedItemId = 1L;
        OrderedItemDTO expectedOrderedItem = new OrderedItemDTO(1L, null, null, 10L, 23.0);
        when(orderedItemService.findOrderedItemById(orderedItemId)).thenReturn(expectedOrderedItem);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order-items/{id}", orderedItemId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedOrderedItem.id()));

        verify(orderedItemService, times(1)).findOrderedItemById(orderedItemId);
    }

    @Test
    public void testIndex() throws Exception {
        // Arrange
        List<OrderedItemDTO> expectedOrderedItems = new ArrayList<>(); // create expected list of OrderedItemDTO objects
        when(orderedItemService.findAll()).thenReturn(expectedOrderedItems);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order-items/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(orderedItemService, times(1)).findAll();
    }

    @Test
    public void testGetByOrder() throws Exception {
        // Arrange
        Long orderId = 1L;
        List<OrderedItemDTO> expectedOrderedItems = new ArrayList<>(); // create expected list of OrderedItemDTO objects
        when(orderedItemService.findOrderedItemByOrder(orderId)).thenReturn(expectedOrderedItems);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order-items/order/{orderId}", orderId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(orderedItemService, times(1)).findOrderedItemByOrder(orderId);
    }

    @Test
    public void testGetByProduct() throws Exception {
        // Arrange
        Long productId = 1L;
        List<OrderedItemDTO> expectedOrderedItems = new ArrayList<>(); // create expected list of OrderedItemDTO objects
        when(orderedItemService.findOrderedItemByProduct(productId)).thenReturn(expectedOrderedItems);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order-items/product/{productId}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(orderedItemService, times(1)).findOrderedItemByProduct(productId);
    }

    @Test
    public void testSave() throws Exception {
        // Arrange
        OrderedItemToSaveDTO orderedItemToSave = new OrderedItemToSaveDTO(null, null, 10L, 23.0);
        OrderedItemDTO expectedSavedItem = new OrderedItemDTO(1L, null, null, 10L, 23.0);
        when(orderedItemService.saveOrderedItem(orderedItemToSave)).thenReturn(expectedSavedItem);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order-items/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"requestedAmount\": 10, \"unitPrice\": 23}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedSavedItem.id()));

        verify(orderedItemService, times(1)).saveOrderedItem(orderedItemToSave);
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        Long orderedItemId = 1L;
        OrderedItemToSaveDTO newItem = new OrderedItemToSaveDTO(null, null, 10L, 23.0);
        OrderedItemDTO expectedUpdatedItem = new OrderedItemDTO(1L, null, null, 10L, 23.0);
        when(orderedItemService.updateOrderedItem(orderedItemId, newItem)).thenReturn(expectedUpdatedItem);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/order-items/{id}", orderedItemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"requestedAmount\": 10, \"unitPrice\": 23}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedUpdatedItem.id()));

        verify(orderedItemService, times(1)).updateOrderedItem(orderedItemId, newItem);
    }

    @Test
    public void testDelete() throws Exception {
        // Arrange
        Long orderedItemId = 1L;

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/order-items/{id}", orderedItemId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("removed"));

        verify(orderedItemService, times(1)).removeOrderedItem(orderedItemId);
    }
}
