package datum.app.clinic.service;

import datum.app.clinic.dto.EmployeeDTO;
import datum.app.clinic.dto.ScheduleDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface EmployeeService {
    Object employee(HttpServletRequest request, long id, List<EmployeeDTO> employeeDTOs);

    Object schedule(long id, ScheduleDTO scheduleDTO);
}
