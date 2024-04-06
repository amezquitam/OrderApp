package ford.group.orderapp.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ClientToSaveDTO(
        @NotNull(message = "Name cannot be null")
        String name,
        @Email
        @NotNull(message = "Email cannot be null")
        String email,
        @NotNull(message = "Address cannot be null")
        String address
) {
}
