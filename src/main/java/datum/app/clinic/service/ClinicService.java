package datum.app.clinic.service;

import datum.app.clinic.dto.*;
import datum.app.clinic.model.*;

import java.util.List;

public interface ClinicService {
    Object clinic(List<ClinicDTO> clinicDTOs);
    Object department(Long id, List<DepartmentDTO> departmentDTOs);
    Object room(Long id, List<RoomDTO> roomDTOs);
    List<Chair>  chair(Long id, List<ChairDTO> chairDTOs);
//    Employee employee(HttpServletRequest request, EmployeeDTO employeeDTO);
}
