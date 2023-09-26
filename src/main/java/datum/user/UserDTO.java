package datum.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Jacksonized
public class UserDTO {
//    @Email
    private String email;
//    @NotBlank
    private String password;
//    @NotEmpty
    private Role role;
    private PersonDTO person;
}
