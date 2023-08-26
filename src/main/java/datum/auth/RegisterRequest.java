package datum.auth;

import datum.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String surname;
  private String patronymic;
  private String email;
  private String birthDay;
  private String phone;

  private String username;
  private String password;
  private Role role;
}
