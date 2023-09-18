package datum.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Jacksonized
public class UserDTO {
    private String email;
    private String password;
    private Role role;
    private PersonDTO person;
}
