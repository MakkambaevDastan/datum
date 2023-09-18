package datum.app.clinic.dto;

import datum.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@Jacksonized
public class EmployeeDTO {
    private Long post;
    private UserDTO user;
    private Boolean everyWeek;
    private Boolean onEvenWeeks;
    private final Map<String, String> start;
    private final Map<String, String> end;
}