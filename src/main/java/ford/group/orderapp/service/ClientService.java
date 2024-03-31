package ford.group.orderapp.service;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.dto.client.ClientToSaveDTO;
import ford.group.orderapp.exception.ClientNotFoundException;

import java.util.List;

public interface ClientService {
    ClientDTO saveClient(ClientToSaveDTO clientDTO);
    ClientDTO updateClient(Long id, ClientToSaveDTO clientDTO);
    ClientDTO findClientById(Long id) throws ClientNotFoundException;
    void removeClient(Long id);
    ClientDTO findClientByEmail(String email);
    List<ClientDTO> findClientsByAddress(String address);
    List<ClientDTO> findClientsByNameStartsWith(String name);

}
