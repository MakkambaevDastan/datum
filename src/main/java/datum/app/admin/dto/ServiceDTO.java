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
public class ServiceDTO  implements Serializable {
    // А23.002 Удаление постоянного зуба простое
    @Schema(example = "A")
    private String view;
    @Schema(example = "23")
    private String section;
    @Schema(example = "Удаление зуба")
    private String sectionName;
    @Schema(example = "1")
    private String number;
    @Schema(example = "Удаление постоянного зуба простое")
    private String serviceName;
    @Schema(example = "")
    private String kg;
    @Schema(example = "true", description = "is dental service")
    private Boolean dental;
}
