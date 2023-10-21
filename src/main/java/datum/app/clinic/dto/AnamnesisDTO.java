package datum.app.clinic.dto;

import datum.app.clinic.model.AnamnesisDetails;
import datum.app.clinic.model.Tooth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class AnamnesisDTO {
    private List<Long> clinicalDiagnosis = new ArrayList<>(); // Клинический диагноз
    private List<Tooth> tooth; // Зубная формула
    private List<AnamnesisDetails> anamnesisDetails = new ArrayList<>();
    private Long personId;
}
