package datum.app.clinic.dto;

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
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class ClinicDTO  implements Serializable {
    @Schema(example = "Голливудская улыбка", description = "Название")
    private String name;
    private List<DepartmentDTO> departments = new ArrayList<>();
}
