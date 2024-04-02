package ford.group.orderapp.service;

import ford.group.orderapp.dto.client.ClientDTO;
import ford.group.orderapp.dto.client.ClientMapper;
import ford.group.orderapp.dto.client.ClientToSaveDTO;
import ford.group.orderapp.entities.Client;
import ford.group.orderapp.exception.ClientNotFoundException;
import ford.group.orderapp.exception.NotAbleToDeleteException;
import ford.group.orderapp.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientMapper clientMapper, ClientRepository clientRepository) {
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDTO saveClient(ClientToSaveDTO clientDTO) {
        Client client = clientMapper.clientSaveDTOToClient(clientDTO);
        Client clientSaved = clientRepository.save(client);
        return clientMapper.clientToClientDTO(clientSaved);
    }

    @Override
    public ClientDTO updateClient(Long id, ClientToSaveDTO clientDTO) {
        return clientRepository.findById(id).map(clientInDB -> {
            clientInDB.setName(clientDTO.name());
            clientInDB.setEmail(clientDTO.email());
            clientInDB.setAddress(clientDTO.address());
            Client clientSaved = clientRepository.save(clientInDB);
            return clientMapper.clientToClientDTO(clientSaved);
        }).orElseThrow(() -> new ClientNotFoundException("Cliente no encontrado"));
    }

    @Override
    public ClientDTO findClientById(Long id) throws ClientNotFoundException {
        Client client = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        return clientMapper.clientToClientDTO(client);
    }

    @Override
    public void removeClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(NotAbleToDeleteException::new);
        clientRepository.delete(client);
    }

    @Override
    public ClientDTO findClientByEmail(String email) {
        Client client = clientRepository.findClientByEmail(email);
        if (Objects.isNull(client))
            throw new ClientNotFoundException("Cliente no encontrado");
        return clientMapper.clientToClientDTO(client);
    }

    @Override
    public List<ClientDTO> findClientsByAddress(String address) {
        List<Client> clients = clientRepository.findClientsByAddress(address);
        if (clients.isEmpty())
            throw new ClientNotFoundException("Clientes no encontrados");
        return clients.stream()
                .map(clientMapper::clientToClientDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientDTO> findClientsByNameStartsWith(String name) {
        List<Client> clients = clientRepository.findClientsByNameStartsWith(name);
        if (clients.isEmpty())
            throw new ClientNotFoundException("Clientes no encontrados");
        return clients.stream()
                .map(clientMapper::clientToClientDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientDTO> findAll() {
        return clientRepository.findAll().stream()
                .map(clientMapper::clientToClientDTO)
                .collect(Collectors.toList());
    }
}
