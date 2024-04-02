package ford.group.orderapp.api.v1;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.dto.client.ClientToSaveDTO;
import ford.group.orderapp.service.ClientService;
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
@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    public void testIndex() throws Exception {
        // Arrange
        List<ClientDTO> expectedClients = new ArrayList<>(); // create expected list of ClientDTO objects
        when(clientService.findAll()).thenReturn(expectedClients);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(clientService, times(1)).findAll();
    }

    @Test
    public void testGet() throws Exception {
        // Arrange
        Long clientId = 1L;
        ClientDTO expectedClient = new ClientDTO(1L, "Johan Liebhard", "jlieb@test.test", "Italia");
        when(clientService.findClientById(clientId)).thenReturn(expectedClient);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/{id}", clientId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedClient.id()));

        verify(clientService, times(1)).findClientById(clientId);
    }

    @Test
    public void testCreate() throws Exception {
        // Arrange
        ClientToSaveDTO clientToSave = new ClientToSaveDTO("Johan Liebhard", "jlieb@test.test", "Italia");
        ClientDTO expectedSavedClient = new ClientDTO(1L, "Johan Liebhard", "jlieb@test.test", "Italia");
        when(clientService.saveClient(clientToSave)).thenReturn(expectedSavedClient);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Johan Liebhard\", \"email\":\"jlieb@test.test\", \"address\":\"Italia\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedSavedClient.id()));

        verify(clientService, times(1)).saveClient(clientToSave);
    }

    @Test
    public void testFindByEmail() throws Exception {
        // Arrange
        String email = "test@example.com";
        ClientDTO expectedClient = new ClientDTO(1L, "Johan Liebhard", "jlieb@test.test", "Italia");
        when(clientService.findClientByEmail(email)).thenReturn(expectedClient);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/email/{email}", email))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(expectedClient.email()));

        verify(clientService, times(1)).findClientByEmail(email);
    }
}
