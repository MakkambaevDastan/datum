package datum.app.clinic.request;

import datum.app.clinic.dto.DepartmentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class clinicCreate {
    private DepartmentDTO head;
    private List<DepartmentDTO> departments;
}
