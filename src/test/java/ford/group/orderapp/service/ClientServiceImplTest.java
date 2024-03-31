package ford.group.orderapp.service;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.dto.client.ClientToSaveDTO;
import ford.group.orderapp.entities.Client;
import ford.group.orderapp.repository.ClientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    Client client;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(48L)
                .name("Test Client")
                .email("testclient@test.test")
                .address("Av Test CR 1 1 1").build();
    }

    @Test
    void saveClient() {
        given(clientRepository.save(any())).willReturn(client);

        ClientToSaveDTO clientToSave = new ClientToSaveDTO(
                "Test Client",
                "testclient@test.test",
                "Av Test"
        );

        ClientDTO savedClient = clientService.saveClient(clientToSave);

        assertThat(savedClient.id()).isNotNull();
        assertThat(savedClient.id()).isEqualTo(48);
        assertThat(savedClient.name()).isEqualTo("Test Client");
    }

    @Test
    void updateClient() {
    }

    @Test
    void findClientById() {
        Long clientId = 48L;

        given(clientRepository.findById(clientId))
                .willReturn(Optional.of(client));

        ClientDTO client =  clientService.findClientById(clientId);

        assertThat(client).isNotNull();
    }

    @Test
    void removeClient() {
        Long clientId = 48L;

        willDoNothing().given(clientRepository).delete(any());

        clientService.removeClient(clientId);

        verify(clientRepository, times(1)).delete(any());
    }

    @Test
    void findClientByEmail() {
        String clientEmail = "testclient@test.test";
        given(clientRepository.findClientByEmail(clientEmail))
                .willReturn(client);

        var client = clientService.findClientByEmail(clientEmail);
        var fakeClient = clientService.findClientByEmail("fakeacount@test.test");

        assertThat(client).isNotNull();
        assertThat(fakeClient).isNull();
    }

    @Test
    void findClientsByAddress() {
    }

    @Test
    void findClientsByNameStartsWith() {
    }
}