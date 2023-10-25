package datum.app.admin.dto;

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
public class ICD10DTO  implements Serializable {
    @Schema(example = "K00.19 Сверхкомплектные зубы неуточненные",
            description = "ICD-10 code (K00.19) and Description")
    private String name;
    @Schema(example = "true", description = "is dental diagnosis")
    private Boolean dental;
}
