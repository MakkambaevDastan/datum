package datum.app.clinic.dto;

import datum.app.admin.model.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class AppointmentDTO {
    private String description;
    private Instant start;
    private Integer minute;
//    private Long employee;
    private Person person;
}
