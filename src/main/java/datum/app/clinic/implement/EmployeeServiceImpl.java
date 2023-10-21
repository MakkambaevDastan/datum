package datum.app.clinic.implement;

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

import datum.Main;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final AuthenticationService authenticationService;
    private final DepartmentRepository departmentRepository;
    private final PostRepository postRepository;

    //    @Override
//    public List<Employee> employee(long id, List<EmployeeDTO> employeeDTOs) {
//        var userId = Main.getUserId();
////        if(userId.isPresent()) {
//        var department = departmentRepository.getWhereIdAndUserId(id, userId, "OWNER");
//        if (department.isPresent()) {
//            String email = SecurityContextHolder.getContext().getAuthentication().getName();
//            List<Employee> employees = EmployeeMapper.INSTANCE.convert(employeeDTOs, postRepository);
//            User user = null;
//            for (Employee employee : employees) {
//                if (!Objects.isNull(employee.getUser()) &&
//                    !userRepository.existsByEmailIgnoreCase(
//                            email = employee.getUser().getEmail().trim().toLowerCase()
//                    )
//                ) authenticationService.register(employee.getUser());
//                user = userRepository.findByEmailIgnoreCase(email).orElseThrow();
//                employee.setUser(user);
//                employeeRepository.save(employee);
//                if (Objects.isNull(department.get().getEmployees()))
//                    department.get().setEmployees(List.of(employee));
//                else
//                    department.get().getEmployees().add(employee);
//            }
//            departmentRepository.save(department.get());
//            return department.get().getEmployees();
//        }
//        throw new ExceptionApp(404, "Отдел не найден");
////        } throw new ExceptionApp(404, "Пользователь не найден");
//    }
//
    @Override
    public List<Employee> employee() {
        var user = userRepository.findById(Main.getUserId());
        if (user.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return user.get().getEmployee();
    }
//
//    @Override
//    public Schedule schedule(long id, Schedule schedule) {
//        Employee employee = employeeRepository.findById(id).orElseThrow();
//        employee.setSchedule(schedule);
//        employeeRepository.save(employee);
//        return schedule;
//    }

    @Override
    public List<Employee> get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId
    ) {
        var empl = employeeRepository.findAllByDepartmentIdAndClinicId(departmentId, clinicId);
        if (empl.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return empl.get();
    }

    @Override
    public Employee get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id
    ) {
        var empl = employeeRepository.findByIdAndClinicId(
                id,
                clinicId
        );
        if (empl.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return empl.get();
    }

    @Override
    public List<Employee> create(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            List<Employee> employees
    ) {
        var userId = Main.getUserId();
        var department =
                departmentRepository.getWhereIdAndUserId(
                        departmentId,
                        userId,
                        "OWNER"
                );
        if (department.isPresent()) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user;
            for (Employee employee : employees) {
                if (!Objects.isNull(employee.getUser()) &&
                    !userRepository.existsByEmailIgnoreCase(
                            email = employee.getUser().getEmail().trim().toLowerCase()
                    )
                ) authenticationService.register(employee.getUser());
                user = userRepository.findByEmailIgnoreCase(email).orElseThrow();
                employee.setUser(user);
                employeeRepository.save(employee);
                if (Objects.isNull(department.get().getEmployees()))
                    department.get().setEmployees(List.of(employee));
                else
                    department.get().getEmployees().add(employee);
            }
            departmentRepository.save(department.get());
            return department.get().getEmployees();
        }
        throw new ExceptionApp(404, "Отдел не найден");
    }

    @Override
    public Employee update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long postId
    ) {
        employeeRepository.updatePostByClinicId(id, postId, clinicId);
        var employee = employeeRepository.findById(id);
        if (employee.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return employee.get();
    }

    @Override
    public Employee update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            Schedule schedule
    ) {
        employeeRepository.updateScheduleByClinicId(id, schedule, clinicId);
        var employee = employeeRepository.findById(id);
        if (employee.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return employee.get();
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
