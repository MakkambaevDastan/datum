package datum.app.clinic.service;

import datum.app.clinic.dto.*;
import datum.app.clinic.model.*;

import java.util.List;

public interface ClinicService {
    List<Clinic> clinic(ClinicDTO clinicDTO);
    List<Clinic> clinic();
    List<Privilege> privilege(Long id);
    List<Privilege> privilege(List<Privilege> privileges);
    List<Department> department(Long id, List<DepartmentDTO> departmentDTOs);
    List<Room> room(Long id, List<RoomDTO> roomDTOs);
    List<Chair>  chair(Long id, List<ChairDTO> chairDTOs);
//    List<Post> post(List<Post> posts);
//    Employee employee(HttpServletRequest request, EmployeeDTO employeeDTO);
}
