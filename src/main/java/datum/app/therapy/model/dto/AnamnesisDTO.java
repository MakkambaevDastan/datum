package datum.app.therapy.model.dto;

import datum.app.therapy.model.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    private Long employee;
    private Long person;
}
