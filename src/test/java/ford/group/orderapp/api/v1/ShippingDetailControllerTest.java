package ford.group.orderapp.api.v1;

import ford.group.orderapp.dto.shippingdetail.ShippingDetailDTO;
import ford.group.orderapp.dto.shippingdetail.ShippingDetailToSaveDTO;
import ford.group.orderapp.service.ShippingDetailService;
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

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ShippingDetailController.class)
public class ShippingDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShippingDetailService shippingDetailService;

    @Test
    public void testGet() throws Exception {
        // Arrange
        Long shippingDetailId = 1L;
        ShippingDetailDTO expectedShippingDetail = new ShippingDetailDTO(1L, null, "Santa Marta", "Dex", "66516654A6F5ADF");
        when(shippingDetailService.findShippingDetailById(shippingDetailId)).thenReturn(expectedShippingDetail);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shipping/{id}", shippingDetailId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedShippingDetail.id()));

        verify(shippingDetailService, times(1)).findShippingDetailById(shippingDetailId);
    }

    @Test
    public void testIndex() throws Exception {
        // Arrange
        List<ShippingDetailDTO> expectedShippingDetails = List.of(
                new ShippingDetailDTO(1L, null, "Santa Marta", "Dex", "66516654A6F5ADF")
        );
        when(shippingDetailService.findAll()).thenReturn(expectedShippingDetails);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shipping/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(shippingDetailService, times(1)).findAll();
    }

    @Test
    public void testGetByOrder() throws Exception {
        // Arrange
        Long orderId = 1L;
        ShippingDetailDTO expectedShippingDetail = new ShippingDetailDTO(1L, null, "Santa Marta", "Dex", "66516654A6F5ADF");
        when(shippingDetailService.findShippingDetailByOrder(orderId)).thenReturn(expectedShippingDetail);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shipping/order/{orderId}", orderId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedShippingDetail.id()));

        verify(shippingDetailService, times(1)).findShippingDetailByOrder(orderId);
    }

    @Test
    public void testGetByCarrier() throws Exception {
        // Arrange
        String carrierName = "DHL";
        List<ShippingDetailDTO> expectedShippingDetails = List.of(
                new ShippingDetailDTO(1L, null, "Santa Marta", "Dex", "66516654A6F5ADF")
        );
        when(shippingDetailService.findShippingDetailsByDeliverer(carrierName)).thenReturn(expectedShippingDetails);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shipping/carrier")
                        .param("name", carrierName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(shippingDetailService, times(1)).findShippingDetailsByDeliverer(carrierName);
    }

    @Test
    public void testSave() throws Exception {
        // Arrange
        ShippingDetailToSaveDTO shippingDetailToSave = new ShippingDetailToSaveDTO(null, "Santa Marta", "Dex", "66516654A6F5ADF");
        ShippingDetailDTO expectedSavedItem = new ShippingDetailDTO(1L, null, "Santa Marta", "Dex", "66516654A6F5ADF");
        when(shippingDetailService.saveShippingDetail(shippingDetailToSave)).thenReturn(expectedSavedItem);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shipping/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"address\":\"Santa Marta\", \"deliverer\": \"Dex\", \"trackingNumber\": \"66516654A6F5ADF\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedSavedItem.id()));

        verify(shippingDetailService, times(1)).saveShippingDetail(shippingDetailToSave);
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        Long shippingDetailId = 1L;
        ShippingDetailToSaveDTO updatedShippingDetail = new ShippingDetailToSaveDTO(null, "Santa Marta", "Dex", "66516654A6F5ADF");
        ShippingDetailDTO expectedUpdatedItem = new ShippingDetailDTO(1L, null, "Santa Marta", "Dex", "66516654A6F5ADF");
        when(shippingDetailService.updateShippingDetail(shippingDetailId, updatedShippingDetail)).thenReturn(expectedUpdatedItem);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/shipping/{id}", shippingDetailId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"address\":\"Santa Marta\", \"deliverer\": \"Dex\", \"trackingNumber\": \"66516654A6F5ADF\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedUpdatedItem.id()));

        verify(shippingDetailService, times(1)).updateShippingDetail(shippingDetailId, updatedShippingDetail);
    }

    @Test
    public void testDelete() throws Exception {
        // Arrange
        Long shippingDetailId = 1L;

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/shipping/{id}", shippingDetailId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("removed"));

        verify(shippingDetailService, times(1)).removeShippingDetail(shippingDetailId);
    }
}
