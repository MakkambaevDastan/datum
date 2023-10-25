package datum.app.clinic.dto;

import datum.app.admin.model.Person;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Schema
public class AppointmentDTO  implements Serializable {
    @Schema(example = "Первичный, консультация", description = "Описание")
    private String description;
    @Schema(example = "2023-10-24T10:00:00.000Z")
    private Instant start;
    @Schema(example = "60")
    private Integer minute;
    @Schema(example = "12")
    private Person person;
}
