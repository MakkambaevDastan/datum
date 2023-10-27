package datum.app.clinic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Schema
public class AppointmentDTO  implements Serializable {

    @Schema(example = "Первичный, консультация")
    private String description;

    @Schema(example = "2023-10-24T10:00:00.000Z")
    @NotNull
    private Instant start;

    @NotNull
    @Schema(example = "60")
    private Integer minute;

    @Schema
    private Long person;

    @Schema
    private List<Long> treatments = new ArrayList<>();
}
