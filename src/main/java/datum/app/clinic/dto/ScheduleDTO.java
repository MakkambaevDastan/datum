package datum.app.clinic.dto;

import datum.app.clinic.model.Day;
//import datum.app.clinic.model.ScheduleID;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class ScheduleDTO {
    private Map<String, String> start;
    private Map<String, String> end;
}
