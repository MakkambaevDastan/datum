package datum.app.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@Jacksonized
public class PostDTO  implements Serializable {
    @Schema(example = "DENTIST")
    private String code;
    @Schema(example = "Dentist")
    private String en;
    @Schema(example = "Врач стоматолог")
    private String ru;
    @Schema(example = "Тиш доктур")
    private String kg;
}
