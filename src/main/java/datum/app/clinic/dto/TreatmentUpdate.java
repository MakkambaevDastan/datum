package datum.app.clinic.dto;

import datum.app.clinic.model.MonetaryAmount;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class TreatmentUpdate {
    private String description;
    private MonetaryAmount discount;
}
