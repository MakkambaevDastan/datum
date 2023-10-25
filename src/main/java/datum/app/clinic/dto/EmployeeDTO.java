package datum.app.clinic.dto;

import datum.app.clinic.model.*;
import datum.authenticate.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@Jacksonized
@Schema
public class EmployeeDTO implements Serializable {
    @NotBlank
    @Schema(example = "DENTIST", description = "postCode")
    private String post;
    @Schema(implementation = UserDTO.class)
    private UserDTO user;
    @Schema(implementation = Schedule.class)
    private Schedule schedule = new Schedule();
}