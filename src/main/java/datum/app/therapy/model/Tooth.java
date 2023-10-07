package datum.app.therapy.model;

import jakarta.persistence.*;
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
public class Tooth implements Serializable {
    private Long icd10;
    private Integer number;
    @Enumerated(EnumType.STRING)
    private ToothCondition condition;
    @Enumerated(EnumType.STRING)
    private ToothMobility mobility;
}
