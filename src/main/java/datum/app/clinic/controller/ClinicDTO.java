package datum.app.clinic.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClinicDTO {
    private DepartmentDTO head;
    private List<DepartmentDTO> departments;
}
