package datum.app.clinic.controller;

import datum.app.clinic.model.Employee;
import datum.app.clinic.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping("")
    public ResponseEntity<List<Employee>> employee(){
        return ResponseEntity.ok(employeeService.employee());
    }
}
