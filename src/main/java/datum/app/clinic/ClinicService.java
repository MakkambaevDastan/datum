package datum.app.clinic;

import datum.app.clinic.model.*;
import datum.app.clinic.model.dto.ChairDTO;
import datum.app.clinic.model.dto.ClinicDTO;
import datum.app.clinic.model.dto.DepartmentDTO;
import datum.app.clinic.model.dto.RoomDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public interface ClinicService {
    Object clinic(ClinicDTO clinicDTO);
    List<Clinic> clinic();
    List<Privilege> privilege(Long id);
    List<Privilege> privilege(List<Privilege> privileges);
    List<Department> department(Long id, List<DepartmentDTO> departmentDTOs);
    List<Room> room(Long id, List<RoomDTO> roomDTOs);
    List<Chair>  chair(Long id, List<ChairDTO> chairDTOs);
}
