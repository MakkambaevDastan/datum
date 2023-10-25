package datum.authenticate;

import datum.app.admin.dto.PersonDTO;
import datum.app.clinic.model.ToothCondition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Jacksonized
@Schema
public class UserDTO {
    @Email
    @Schema(example = "user@gmail.com", description = "email")
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @Schema(implementation = PersonDTO.class)
    private PersonDTO person;
}
