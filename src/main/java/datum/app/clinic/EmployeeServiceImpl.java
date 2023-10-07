package datum.app.clinic;

import datum.app.clinic.model.dto.EmployeeDTO;
import datum.app.clinic.model.dto.EmployeeMapper;
import datum.app.clinic.model.Employee;
import datum.app.clinic.model.Schedule;
import datum.app.clinic.model.repositoy.DepartmentRepository;
import datum.app.clinic.model.repositoy.EmployeeRepository;
import datum.app.admin.model.repository.PostRepository;
import datum.authenticate.AuthenticationService;
import datum.authenticate.user.User;
import datum.authenticate.user.UserRepository;
import datum.config.exception.ExceptionApp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import  datum.Main;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final AuthenticationService authenticationService;
    private final DepartmentRepository departmentRepository;
    private final PostRepository postRepository;

    @Override
    public List<Employee> employee(long id, List<EmployeeDTO> employeeDTOs) {
        var userId = Main.getUserId();
        if(userId.isPresent()) {
            var department = departmentRepository.getWhereIdAndUserId(id, userId.get(), "OWNER");
            if(department.isPresent()) {
                String email = SecurityContextHolder.getContext().getAuthentication().getName();
                List<Employee> employees = EmployeeMapper.INSTANCE.list(employeeDTOs, postRepository);
                User user=null;
                for (Employee employee : employees) {
                    if (!Objects.isNull(employee.getUser()) &&
                        !userRepository.existsByEmailIgnoreCase(
                                email = employee.getUser().getEmail().trim().toLowerCase()
                        )
                    ) authenticationService.register(employee.getUser());
                    user = userRepository.findByEmailIgnoreCase(email).orElseThrow();
                    employee.setUser(user);
                    employeeRepository.save(employee);
                    if(Objects.isNull(department.get().getEmployees()))
                        department.get().setEmployees(List.of(employee));
                    else
                        department.get().getEmployees().add(employee);
                }
                departmentRepository.save(department.get());
                return department.get().getEmployees();
            } throw new ExceptionApp(404, "Отдел не найден");
        } throw new ExceptionApp(404, "Пользователь не найден");
    }

    @Override
    public List<Employee> employee() {
        User user = userRepository.findByEmailIgnoreCase(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow();
        return user.getEmployee();
    }

    @Override
    public Schedule schedule(long id, Schedule schedule) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employee.setSchedule(schedule);
        employeeRepository.save(employee);
        return schedule;
    }

}
