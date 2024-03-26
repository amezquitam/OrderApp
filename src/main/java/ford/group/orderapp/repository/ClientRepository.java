package ford.group.orderapp.repository;

import ford.group.orderapp.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findClientByEmail(String email);

    List<Client> findClientsByAddress(String address);

    List<Client> findClientsByNameStartsWith(String name);
}
