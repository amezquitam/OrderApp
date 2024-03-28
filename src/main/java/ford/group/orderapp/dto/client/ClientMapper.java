package ford.group.orderapp.dto.client;

import ford.group.orderapp.entities.Client;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ClientMapper {
    ClientDTO clientToClientDTO(Client client);
    Client clientDTOToClient(ClientDTO clientDTO);
    Client clientSaveDTOToClient(ClientToSaveDTO clientToSaveDTO);
}
