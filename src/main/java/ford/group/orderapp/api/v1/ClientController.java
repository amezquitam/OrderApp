package ford.group.orderapp.api.v1;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.dto.client.ClientToSaveDTO;
import ford.group.orderapp.exception.ClientNotFoundException;
import ford.group.orderapp.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/customers")
public class ClientController {

    ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    ResponseEntity<List<ClientDTO>> index() {
        List<ClientDTO> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    ResponseEntity<ClientDTO> get(@PathVariable("id") Long clientId) {
        try {
            var client = clientService.findClientById(clientId);
            return ResponseEntity.ok(client);
        } catch (ClientNotFoundException err) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    ResponseEntity<ClientDTO> create(@RequestBody ClientToSaveDTO clientToSave) {
        // validations

        // save
        ClientDTO savedClient = clientService.saveClient(clientToSave);

        return ResponseEntity.ok(savedClient);
    }

    @GetMapping("/email/{email}")
    ResponseEntity<ClientDTO> findByEmail(@PathVariable("email") String email) {
        try {
            ClientDTO client = clientService.findClientByEmail(email);

            return ResponseEntity.ok(client);

        } catch (ClientNotFoundException err) {
            return ResponseEntity.notFound().build();
        }
    }

}
