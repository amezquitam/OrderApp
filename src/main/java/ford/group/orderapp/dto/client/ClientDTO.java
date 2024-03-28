package ford.group.orderapp.dto.client;

public record ClientDTO(
        Long id,
        String name,
        String email,
        String address
) {
}
