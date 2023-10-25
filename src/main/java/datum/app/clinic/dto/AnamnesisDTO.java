package datum.app.clinic.dto;

import datum.app.clinic.model.AnamnesisDetails;
import datum.app.clinic.model.Tooth;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Schema
public class AnamnesisDTO implements Serializable {
    @Schema(example = "123", description = "icd10Id")
    private List<Long> clinicalDiagnosis = new ArrayList<>(); // Клинический диагноз
    @Schema(description = "Зубная формула нотация - FDI World Dental Federation notation")
    private List<Tooth> tooth = new ArrayList<>(); // Зубная формула
    @Schema
    private List<AnamnesisDetails> anamnesisDetails = new ArrayList<>();
    @Schema(example = "121223", description = "personId")
    private Long personId;
}
