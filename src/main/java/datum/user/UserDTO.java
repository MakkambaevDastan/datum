package datum.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private String email;
    private String password;
//    @Enumerated(EnumType.STRING)
    private Role role;
    private PersonDTO person;
}
