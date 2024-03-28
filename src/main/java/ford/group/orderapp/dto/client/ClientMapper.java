package ford.group.orderapp.dto.client;

import ford.group.orderapp.entities.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {
    ClientDTO clientToClientDTO(Client client);
    Client clientDTOToClient(ClientDTO clientDTO);
}
