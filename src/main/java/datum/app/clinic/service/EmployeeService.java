package datum.app.clinic.service;

import datum.app.clinic.dto.EmployeeDTO;
import datum.app.clinic.dto.ScheduleDTO;
import datum.app.clinic.model.Employee;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface EmployeeService {
    List<Employee> employee(HttpServletRequest request, long id, List<EmployeeDTO> employeeDTOs);
    List<Employee> employee();

    Employee schedule(long id, ScheduleDTO scheduleDTO);

}
