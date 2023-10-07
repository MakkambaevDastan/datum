package datum.app.clinic.model.dto;

//import datum.app.clinic.model.Schedule;

import datum.app.clinic.model.Schedule;
import datum.authenticate.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@AllArgsConstructor
@Jacksonized
public class EmployeeDTO {
    private String post;
    private UserDTO user;
    private Schedule schedule;
}