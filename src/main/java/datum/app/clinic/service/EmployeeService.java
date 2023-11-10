package datum.app.clinic.service;

import datum.app.admin.model.Post;
import datum.app.clinic.dto.EmployeeDTO;
//import datum.app.clinic.dto.ScheduleDTO;
import datum.app.clinic.model.Employee;
//import datum.app.clinic.model.Schedule;
import datum.app.clinic.model.Schedule;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface EmployeeService {
//    List<Employee> employee(long id, List<EmployeeDTO> employeeDTOs);
////    Employee employee(long id, Employee employee, String email);
    List<Employee> getEmployeeUser();
//    Schedule schedule(long id, Schedule schedule);

    List<Employee> get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId
    );
    Employee get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id
    );


    List<Employee> create(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            List<Employee> employees
    );
    List<Employee> createByOwner(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            List<Employee> employees
    );
    Post update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long postId
    );
    Schedule update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            Schedule schedule
    );
    void delete(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id
    );
}
