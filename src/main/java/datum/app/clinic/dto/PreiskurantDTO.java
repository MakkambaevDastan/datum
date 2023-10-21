package datum.app.clinic.dto;

import datum.app.clinic.model.MonetaryAmount;
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
public class PreiskurantDTO {
    private Long service;
    private MonetaryAmount price;
    private String description;
//    private List<Long> icd10;
}
