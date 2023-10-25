package datum.app.clinic.controller;

import datum.Main;
import datum.app.admin.model.Post;
import datum.app.admin.repository.PostRepository;
import datum.app.clinic.dto.EmployeeDTO;
import datum.app.clinic.mapping.EmployeeMapper;
import datum.app.clinic.model.Employee;
import datum.app.clinic.model.Schedule;
import datum.app.clinic.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/CLINIC/{clinicId}/employee/{employeeId}/department/{departmentId}/employee")
public class EmployeeController {
    private final PostRepository postRepository;
    private final EmployeeService employeeService;
    //=================================================================================================
    @GetMapping
    public ResponseEntity<List<Employee>> getEmployee(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId
    ) {
        return ResponseEntity.ok(
                employeeService.get(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId)
                )
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id
    ) {
        return ResponseEntity.ok(
                employeeService.get(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        Main.parseLong(id)
                )
        );
    }
    @PostMapping
    public ResponseEntity<List<Employee>> createEmployee(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @RequestBody List<EmployeeDTO> employeeDTOs
    ) {
        return ResponseEntity.ok(
                employeeService.create(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        EmployeeMapper.INSTANCE.convert(employeeDTOs, postRepository)
                )
        );
    }

    @PutMapping("/{id}/post/{postId}")
    public ResponseEntity<Post> updateEmployee(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id,
            @PathVariable("postId") String postId
    ) {
        return ResponseEntity.ok(
                employeeService.update(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        Main.parseLong(id),
                        Main.parseLong(postId)
                )
        );
    }

    @PutMapping("/{id}/schedule")
    public ResponseEntity<Schedule> updateEmployee(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id,
            @RequestBody Schedule schedule
    ) {
        return ResponseEntity.ok(
                employeeService.update(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        Main.parseLong(id),
                        schedule
                )
        );
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id
    ) {
        employeeService.delete(
                request,
                Main.parseLong(clinicId),
                Main.parseLong(employeeId),
                Main.parseLong(departmentId),
                Main.parseLong(id)
        );
    }
    //=================================================================================================
}
