package datum.app.clinic.controller;

import datum.app.clinic.model.EmployeeRole;
import datum.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private EmployeeRole role;
    private UserDTO user;
    private Boolean yourself;
}
