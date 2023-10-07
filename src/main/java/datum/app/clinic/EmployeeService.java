package datum.app.clinic;

import datum.app.clinic.model.dto.EmployeeDTO;
//import datum.app.clinic.dto.ScheduleDTO;
import datum.app.clinic.model.Employee;
//import datum.app.clinic.model.Schedule;
import datum.app.clinic.model.Schedule;

import java.util.List;

public interface EmployeeService {
    List<Employee> employee(long id, List<EmployeeDTO> employeeDTOs);
//    Employee employee(long id, Employee employee, String email);
    List<Employee> employee();
    Schedule schedule(long id, Schedule schedule);
//    List<Schedule> schedule(long id, Long scheduleId);


}
