package ford.group.orderapp.service;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.dto.client.ClientMapperImpl;
import ford.group.orderapp.dto.client.ClientToSaveDTO;
import ford.group.orderapp.entities.Client;
import ford.group.orderapp.exception.ClientNotFoundException;
import ford.group.orderapp.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientServiceImpl clientService;

    Client client;

    @BeforeEach
    void setUp() {
        clientService = new ClientServiceImpl(new ClientMapperImpl(), clientRepository);

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
        Long clientId = 48L;

        given(clientRepository.save(any())).willReturn(client);
        given(clientRepository.findById(any()))
                .willReturn(Optional.empty());

        given(clientRepository.findById(clientId))
                .willReturn(Optional.of(client));

        ClientToSaveDTO clientToSave = new ClientToSaveDTO(
                "Test Client update",
                "testclientupdate@test.test",
                "Av Test update"
        );

        ClientDTO updatedClient = clientService.updateClient(clientId,clientToSave);

        assertThat(updatedClient.name()).isEqualTo("Test Client update");
        assertThat(updatedClient.email()).isEqualTo("testclientupdate@test.test");
        assertThat(updatedClient.address()).isEqualTo("Av Test update");
    }

    @Test
    void findClientById() {
        Long clientId = 48L;

        given(clientRepository.findById(any()))
                .willReturn(Optional.empty());

        given(clientRepository.findById(clientId))
                .willReturn(Optional.of(client));

        ClientDTO client = clientService.findClientById(clientId);

        assertThat(client).isNotNull();
        assertThat(client.id()).isEqualTo(48);
    }

    @Test
    void removeClient() {
        Long clientId = 48L;

        willDoNothing().given(clientRepository).delete(any());
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        clientService.removeClient(clientId);

        verify(clientRepository, times(1)).delete(any());
    }

    @Test
    void findClientByEmail() {
        String clientEmail = "testclient@test.test";
        given(clientRepository.findClientByEmail(clientEmail))
                .willReturn(client);

        var client = clientService.findClientByEmail(clientEmail);

        assertThat(client).isNotNull();

        assertThrows(
                ClientNotFoundException.class,
                () -> clientService.findClientByEmail("fakeacount@test.test"),
                "Cliente no encontrado"
        );

    }

    @Test
    void findClientsByAddress() {
        String clientAddress = "Av Test CR 1 1 1";
        given(clientRepository.findClientsByAddress(clientAddress))
                .willReturn(List.of(client));

        var listClients = clientService.findClientsByAddress(clientAddress);

        assertThat(listClients).isNotEmpty();

        assertThat(listClients.get(0).id()).isEqualTo(48);
    }

    @Test
    void findClientsByNameStartsWith() {
        String nameStartsWith = "Test";
        given(clientRepository.findClientsByNameStartsWith(nameStartsWith))
                .willReturn(List.of(client));

        var listClients = clientService.findClientsByNameStartsWith(nameStartsWith);

        assertThat(listClients).isNotEmpty();

        assertThat(listClients.get(0).id()).isEqualTo(48);
        assertThat(listClients.get(0).name()).isEqualTo("Test Client");
    }
}