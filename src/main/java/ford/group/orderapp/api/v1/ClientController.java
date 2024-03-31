package ford.group.orderapp.api.v1;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController("/api/v1/client")
public class ClientController {

    ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    ResponseEntity<ClientDTO> get(@PathVariable("id") Long clientId) {
        var client = clientService.findClientById(clientId);
        return ResponseEntity.ok(client);
    }

}
