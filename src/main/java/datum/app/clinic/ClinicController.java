package datum.app.clinic;

import datum.app.clinic.controller.*;
import datum.app.clinic.model.EmployeeRole;
import datum.app.clinic.service.ClinicService;
import datum.auth.AuthenticationResponse;
import datum.auth.AuthenticationService;
import datum.config.JwtService;
import datum.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
public class ClinicController {
    private final ClinicService clinicService;
    private final AuthenticationService authenticationService;

    @PostMapping("create")
    public ResponseEntity<Boolean> employee(
            HttpServletRequest request,
            @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(clinicService.employee(request, employeeDTO));
    }

    @GetMapping("read")
    public ResponseEntity<Boolean> readEmployee() {
        return ResponseEntity.ok(true);
    }

    @PostMapping("update")
    public ResponseEntity<Boolean> updateEmployee() {
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("delete")
    public ResponseEntity<Boolean> deleteEmployee() {
        return ResponseEntity.ok(true);
    }

    @PostMapping("/clinic/create")
    public ResponseEntity<Boolean> createClinic(@RequestBody ClinicDTO ClinicDTO) {
        return ResponseEntity.ok(clinicService.addClinic(ClinicDTO));
    }

    @DeleteMapping("/clinic/delete")
    public ResponseEntity<Boolean> deleteClinic(@RequestParam("clinic_id") Long id) {
        return ResponseEntity.ok(true);
    }

    @PostMapping("/clinic/department/create")
    public ResponseEntity<Boolean> createDepartment(
            @RequestBody DepartmentDTO departmentDTO,
            @RequestParam("clinic_id") Long id
    ) {
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/clinic/department/delete")
    public ResponseEntity<Boolean> deleteDepartment(@RequestParam("department_id") Long id) {
        return ResponseEntity.ok(true);
    }

    @PostMapping("/clinic/department/employee/add")
    public ResponseEntity<Boolean> addEmployee(
            HttpServletRequest headRequest,
            @RequestBody EmployeeDTO employeeDTO) {
        var is = authenticationService.confirmToken(authenticationService.register(headRequest, employeeDTO.getUser()));
        if (is.getClass() == AuthenticationResponse.class) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @DeleteMapping("/clinic/department/employee/delete")
    public ResponseEntity<Boolean> deleteEmployee(@RequestParam("employee_id") Long id) {
        return ResponseEntity.ok(true);
    }

    @GetMapping("/clinic/department/employee/role")
    public ResponseEntity<List<EmployeeRole>> getRole() {
        List<EmployeeRole> role = List.of(EmployeeRole.values());
        return ResponseEntity.ok(role);
    }

    @PostMapping("/clinic/department/room/create")
    public ResponseEntity<Boolean> createRoom(
            @RequestBody RoomDTO roomDTO,
            @RequestParam("department_id") Long id
    ) {
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/clinic/department/room/delete")
    public ResponseEntity<Boolean> deleteRoom(@RequestParam("room_id") Long id) {
        return ResponseEntity.ok(true);
    }

    @PostMapping("/clinic/department/room/chair/create")
    public ResponseEntity<Boolean> createChair(
            @RequestBody ChairDTO chairDTO,
            @RequestParam("room_id") Long id
    ) {
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/clinic/department/room/chair/delete")
    public ResponseEntity<Boolean> deleteChair(@RequestParam("chair_id") Long id) {
        return ResponseEntity.ok(true);
    }
}
