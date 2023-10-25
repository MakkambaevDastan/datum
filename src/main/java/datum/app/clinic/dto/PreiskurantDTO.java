package datum.app.clinic.dto;

import datum.app.clinic.model.MonetaryAmount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Schema
public class PreiskurantDTO implements Serializable {
    @Schema(example="serviceId")
    private Long service;
    @Schema
    private MonetaryAmount price;
    @Schema
    private String description;
//    private List<Long> icd10;
}
