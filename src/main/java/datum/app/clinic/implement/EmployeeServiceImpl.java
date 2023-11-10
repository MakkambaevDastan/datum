package datum.app.clinic.implement;

import datum.app.admin.model.Post;
import datum.app.clinic.model.Department;
import datum.app.clinic.model.Employee;
import datum.app.clinic.model.Schedule;
import datum.app.clinic.repositoy.DepartmentRepository;
import datum.app.clinic.repositoy.EmployeeRepository;
import datum.app.admin.repository.PostRepository;
import datum.app.clinic.service.EmployeeService;
import datum.authenticate.AuthenticationService;
import datum.authenticate.User;
import datum.authenticate.UserRepository;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import datum.Main;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final AuthenticationService authenticationService;
    private final DepartmentRepository departmentRepository;
    private final PostRepository postRepository;

    @Override
    public List<Employee> getEmployeeUser() {
        return employeeRepository.findAllByUserId(Main.getUserId())
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
    }

    @Override
    public List<Employee> get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId
    ) {
        return employeeRepository.findAllByDepartmentIdAndClinicId(departmentId, clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
    }

    @Override
    public Employee get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id
    ) {
        return employeeRepository.findByIdAndClinicId(id, clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
    }

    @Override
    public List<Employee> createByOwner(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            List<Employee> employees
    ) {
        Department department = departmentRepository.findByIdAndOwnerId(
                departmentId,
                Main.getUserId()
        ).orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
       return create(department, employees);
    }

    @Override
    public List<Employee> create(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            List<Employee> employees
    ) {
        Department department = departmentRepository.findByIdAndEmployeeId(
                departmentId,
                employeeId
        ).orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
        return create(department, employees);
    }

    private List<Employee> create(Department department, List<Employee> employees) {
        for (Employee employee : employees) {
            String email ="";
            if (!Objects.isNull(employee.getUser()) &&
                    !userRepository.existsByEmailIgnoreCase(
                            email = employee.getUser().getEmail().trim().toLowerCase()
                    )
            ) authenticationService.register(employee.getUser());
            if(!email.isBlank()) {
                Optional<User> user = userRepository.findByEmailIgnoreCase(email);
                if (user.isPresent()) employee.setUser(user.get());
            }
            employeeRepository.save(employee);
            department.getEmployees().add(employee);
        }
        departmentRepository.save(department);
        return department.getEmployees();
    }
    @Override
    public Post update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long postId
    ) {
        employeeRepository.updatePostByClinicId(id, postId, clinicId);
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ExceptionApp(500, Message.INTERNAL_SERVER_ERROR))
                .getPost();
    }

    @Override
    public Schedule update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            Schedule schedule
    ) {
        employeeRepository.updateScheduleByClinicId(id, schedule, clinicId);
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ExceptionApp(500, Message.INTERNAL_SERVER_ERROR))
                .getSchedule();
    }

    @Override
    public void delete(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id
    ) {
        if (!employeeRepository.existsByIdAndClinicId(id, clinicId))
            throw new ExceptionApp(404, Message.NOT_FOUND);
        employeeRepository.deleteById(id);
    }

}
