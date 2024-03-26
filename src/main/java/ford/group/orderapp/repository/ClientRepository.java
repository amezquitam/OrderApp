package ford.group.orderapp.repository;

import ford.group.orderapp.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
