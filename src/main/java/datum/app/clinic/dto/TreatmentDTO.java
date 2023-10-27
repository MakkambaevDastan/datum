package datum.app.clinic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import datum.app.clinic.model.MonetaryAmount;
import datum.app.clinic.model.Tooth;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Schema
public class TreatmentDTO {
    private String description;
    @NotNull
    private Tooth tooth;
    @JsonProperty("preiskurantId")
    @Schema(example = "123")
    @NotNull
    private Long preiskurant;
    @Schema(description = "Скидка")
    private MonetaryAmount discount;
}
