package datum.app.clinic.service;

import datum.app.clinic.dto.EmployeeDTO;
import datum.app.clinic.dto.EmployeeMapper;
import datum.app.clinic.dto.ScheduleDTO;
import datum.app.clinic.model.Day;
import datum.app.clinic.model.Department;
import datum.app.clinic.model.Employee;
import datum.app.clinic.model.repositoy.DepartmentRepository;
import datum.app.clinic.model.repositoy.EmployeeRepository;
import datum.app.clinic.model.repositoy.PostRepository;
import datum.auth.AuthenticationService;
import datum.user.User;
import datum.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final AuthenticationService authenticationService;
    private final DepartmentRepository departmentRepository;
    private final PostRepository postRepository;

    @Override
    public List<Employee> employee(HttpServletRequest request, long id, List<EmployeeDTO> employeeDTOs) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Department department = departmentRepository.findById(id).orElseThrow();
        List<Employee> employees = EmployeeMapper.INSTANCE.list(employeeDTOs, postRepository);
        User user;
        for (Employee employee : employees) {
            if (!Objects.isNull(employee.getUser()) &&
                !userRepository.existsByEmailIgnoreCase(
                        email = employee.getUser().getEmail().trim().toLowerCase()
                )
            ) authenticationService.register(request, employee.getUser());
            user = userRepository.findByEmailIgnoreCase(email).orElseThrow();
            employee.setUser(user);
            employeeRepository.save(employee);
            department.getEmployees().add(employee);
        }
        departmentRepository.save(department);
        return department.getEmployees();
    }

    @Override
    public List<Employee> employee() {
        User user = userRepository.findByEmailIgnoreCase(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow();
        return user.getEmployee();
    }

    @Override
    public Employee schedule(long id, ScheduleDTO scheduleDTO) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        scheduleDTO.getStart().forEach((k, v) -> employee.getStart().put(Day.valueOf(k), LocalTime.parse(v)));
        scheduleDTO.getEnd().forEach((k, v) -> employee.getEnd().put(Day.valueOf(k), LocalTime.parse(v)));
        employeeRepository.save(employee);
        return employee;
    }

}
