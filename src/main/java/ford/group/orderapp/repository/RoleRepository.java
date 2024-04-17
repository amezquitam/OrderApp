package ford.group.orderapp.repository;

import ford.group.orderapp.entities.ERole;
import ford.group.orderapp.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
