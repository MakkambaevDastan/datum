package datum;

import datum.app.clinic.dto.EmployeeDTO;
import datum.app.clinic.dto.EmployeeMapper;
import datum.app.clinic.model.Day;
import datum.app.clinic.model.Employee;
import datum.app.clinic.model.EmployeeRole;
import datum.app.clinic.model.repositoy.EmployeeRepository;
import datum.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class Test {
    private final EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<Object> test() {
        return ResponseEntity.ok("Hello, World!");
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> test(
            @PathVariable("id") String idS,
            @RequestBody List<EmployeeDTO> employeeDTOs) {
        Long id = Long.parseLong(idS);
        List<Employee> employees = EmployeeMapper.INSTANCE.list(employeeDTOs);
        employeeRepository.saveAll(employees);
        return ResponseEntity.ok(employees);
    }
}

