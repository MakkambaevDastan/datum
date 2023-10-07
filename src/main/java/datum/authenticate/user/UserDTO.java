package datum.authenticate.user;

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
