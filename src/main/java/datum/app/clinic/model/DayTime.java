package datum.app.clinic.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalTime;
import java.util.EnumMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Schema
public class DayTime implements Serializable {
    @Schema(example = "2023-10-24T06:00:00.000Z")
    private Instant start;
    @Schema(example = "2023-10-24T15:00:00.000Z")
    private Instant end;
}
