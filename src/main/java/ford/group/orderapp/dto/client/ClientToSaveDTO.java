package ford.group.orderapp.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClientToSaveDTO(
        @NotBlank(message = "Name may not be blank")
        String name,
        @Email
        String email,
        @NotBlank(message = "Address may not be blank")
        String address
) {
}
