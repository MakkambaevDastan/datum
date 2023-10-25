package datum.app.clinic.model;

import datum.config.audit.Auditable;
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
public class AnamnesisDetails  implements Serializable {
    @Schema(example = "Анамнез заболевания")
    private String name;
    @Schema(example = """
    Сутки назад произошел отлом коронки центрального зуба верхней челюсти при приеме пищи.
    Зуб лечен три года назад по поводу осложнений кариеса.
    """)
    private String description;
    @Schema(example = "2023-11-20")
    private String date;
}
