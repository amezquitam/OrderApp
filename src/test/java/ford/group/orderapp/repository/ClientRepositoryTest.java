package ford.group.orderapp.repository;

import ford.group.orderapp.AbstractIntegrationDBTest;
import ford.group.orderapp.entities.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClientRepositoryTest extends AbstractIntegrationDBTest {
    @Autowired
    ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
            clientRepository.deleteAll();
    }

    @Test
    void whenInsertAClientThenClientIdIsNotNull() {
        Client client = Client.builder()
                .name("Johan López")
                .build();

        Client clientSaved = clientRepository.save(client);
        assertThat(clientSaved.getId()).isNotNull();
    }

    @Test
    void whenInsertRepeatedEmailClientsThenUniqueConstraintError() {
        Client client1 = Client.builder()
                .name("Johan López")
                .email("johanlopez@test.email")
                .build();
        Client client2 = Client.builder()
                .name("Johan López")
                .email("johanlopez@test.email")
                .build();

        clientRepository.save(client1);

        Boolean hasException = false;

        try {
            clientRepository.save(client2);
        } catch (DataIntegrityViolationException e) {
            hasException = true;
        }

        assertThat(hasException).isTrue();
    }

    @Test
    void whenFindAllClientsThenGetEmptyList() {
        var clients = clientRepository.findAll();
        assertThat(clients).isEmpty();
    }

    @Test
    void whenFindAllClientsThenGetCorrectSize() {
        var clients = List.of(
                Client.builder().name("Johan Lopez").email("johanlopez@test.com").build(),
                Client.builder().name("Patrick Lopez").email("patricklopez@test.com").build(),
                Client.builder().name("Jack Lopez").email("kikyoulopez@test.com").build(),
                Client.builder().name("Joe Lopez").email("joelopez@test.com").build()
        );

        clientRepository.saveAll(clients);

        var savedClients = clientRepository.findAll();

        assertThat(savedClients).hasSize(4);
        assertThat(savedClients).anyMatch(client -> client.getEmail().equals("kikyoulopez@test.com"));
        assertThat(savedClients).anyMatch(client -> client.getName().equals("Johan Lopez"));
    }

    @Test
    void updateClientTest() {
        var clients = List.of(
                Client.builder().name("Johan Lopez").email("johanlopez@test.com").build(),
                Client.builder().name("Patrick Lopez").email("patricklopez@test.com").build(),
                Client.builder().name("Jack Lopez").email("kikyoulopez@test.com").build(),
                Client.builder().name("Joe Lopez").email("joelopez@test.com").build()
        );

        var savedClients = clientRepository.saveAll(clients);

        var someClient = savedClients.get(0);

        someClient.setName("Jun Hamilton");

        clientRepository.save(someClient);

        savedClients = clientRepository.findAll();

        assertThat(savedClients).hasSize(4);
        assertThat(savedClients).anyMatch(client -> client.getName().equals("Jun Hamilton"));
    }

    @Test
    void deleteClientTest() {
        var clients = List.of(
                Client.builder().name("Johan Lopez").email("johanlopez@test.com").build(),
                Client.builder().name("Patrick Lopez").email("patricklopez@test.com").build(),
                Client.builder().name("Jack Lopez").email("kikyoulopez@test.com").build(),
                Client.builder().name("Joe Lopez").email("joelopez@test.com").build()
        );

        var savedClients = clientRepository.saveAll(clients);

        var userToDelete = savedClients.get(0);
        clientRepository.delete(userToDelete);

        savedClients = clientRepository.findAll();

        assertThat(savedClients).hasSize(3);
        assertThat(savedClients).doesNotContain(userToDelete);
    }

    @Test
    void findClientsByEmailTest() {
        var clients = List.of(
                Client.builder().name("Johan Lopez").email("johanlopez@test.com").build(),
                Client.builder().name("Patrick Lopez").email("patricklopez@test.com").build(),
                Client.builder().name("Jack Lopez").email("kikyoulopez@test.com").build(),
                Client.builder().name("Joe Lopez").email("joelopez@test.com").build()
        );

        clientRepository.saveAll(clients);

        var targetClient = clientRepository.findClientByEmail("joelopez@test.com");

        assertThat(targetClient).isNotNull();
        assertThat(targetClient.getName()).isEqualTo("Joe Lopez");
    }

    @Test
    void findClientsByAddressTest() {
        var clients = List.of(
                Client.builder().name("Johan Lopez").email("johanlopez@test.com").address("K234-65").build(),
                Client.builder().name("Patrick Lopez").email("patricklopez@test.com").address("K134-25").build(),
                Client.builder().name("Jack Lopez").email("kikyoulopez@test.com").address("K256-72").build(),
                Client.builder().name("Joe Lopez").email("joelopez@test.com").address("K267-23").build(),
                Client.builder().name("Marian Lopez").email("marianlopez@test.com").address("K267-23").build()
        );

        clientRepository.saveAll(clients);

        var targetClients = clientRepository.findClientsByAddress("K267-23");

        assertThat(targetClients).hasSize(2);
        assertThat(targetClients).anyMatch(client -> client.getName().equals("Marian Lopez"));
    }

    @Test
    void findClientsByNameStartsWithTest() {
        var clients = List.of(
                Client.builder().name("Patrick Alvarez").email("patrick@test.com").address("K134-25").build(),
                Client.builder().name("Johan Lopez").email("johanlopez@test.com").address("K234-65").build(),
                Client.builder().name("Megan Hernandez").email("Megan@test.com").address("K247-23").build(),
                Client.builder().name("Marian Cruise").email("marian@test.com").address("K267-23").build(),
                Client.builder().name("Jack Sierra").email("kikyou@test.com").address("K256-72").build(),
                Client.builder().name("Megan Zora").email("megan@test.com").address("K231-63").build(),
                Client.builder().name("Joe Gomez").email("joe@test.com").address("K267-23").build()
        );

        clientRepository.saveAll(clients);

        var targetClients = clientRepository.findClientsByNameStartsWith("J");

        assertThat(targetClients).hasSize(3);
        assertThat(targetClients).anyMatch(client -> client.getName().equals("Joe Gomez"));
        assertThat(targetClients).anyMatch(client -> client.getAddress().equals("K267-23"));
    }
}