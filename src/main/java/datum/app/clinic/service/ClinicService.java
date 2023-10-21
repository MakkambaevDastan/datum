package datum.app.clinic.service;

import datum.app.clinic.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public interface ClinicService {
//    Object clinic(ClinicDTO clinicDTO);
//    List<Clinic> clinic();

//    List<Department> department(Long id, List<DepartmentDTO> departmentDTOs);
//    List<Room> room(Long id, List<RoomDTO> roomDTOs);
//    List<Chair>  chair(Long id, List<ChairDTO> chairDTOs);

    List<Clinic> get();
    Clinic get(Long clinicId);

    Clinic create(Clinic clinic);

    String update(long clinicId, String name);

    void delete(long clinicId);
//    boolean isClinicEmployee(Long clinicId, Long userId);
//    List<Long> getEmployeeId(Long clinicId, Long userId);
//     boolean isRightPoint(Long clinicId, Long userId, HttpServletRequest request);
}
