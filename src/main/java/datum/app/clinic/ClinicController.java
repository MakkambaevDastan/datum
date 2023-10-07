package datum.app.clinic;

import datum.app.clinic.model.*;
import datum.app.clinic.model.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clinic")
public class ClinicController {
    private final ClinicService clinicService;
    private final EmployeeService employeeService;
    @GetMapping
    public ResponseEntity<List<Clinic>> clinic() {
        return ResponseEntity.ok(clinicService.clinic());
    }
    @PostMapping
    public ResponseEntity<Object> clinic(
            @RequestBody ClinicDTO clinicDTO
    ) {
        return ResponseEntity.ok(clinicService.clinic(clinicDTO));
    }

    @GetMapping("/{id}/privilege")
    public ResponseEntity<List<Privilege>> privilege(@PathVariable("id") String id){
        return ResponseEntity.ok(clinicService.privilege(Long.parseLong(id)));
    }
    @PatchMapping("/privilege")
    public ResponseEntity<List<Privilege>> privilege(@RequestBody List<Privilege> privileges){
        return ResponseEntity.ok(clinicService.privilege(privileges));
    }
    @PostMapping("/{id}/department")
    public ResponseEntity<List<Department>> department(
            @PathVariable("id") String id,
            @RequestBody List<DepartmentDTO> departmentDTOs
    ) {
        return ResponseEntity.ok(
                clinicService.department(Long.parseLong(id), departmentDTOs)
        );
    }

    @PostMapping("/{id}/room")
    public ResponseEntity<List<Room>> room(
            @PathVariable("id") String id,
            @RequestBody List<RoomDTO> roomDTOs
    ) {
        return ResponseEntity.ok().body(
                clinicService.room(
                        Long.parseLong(id),
                        roomDTOs
                )
        );
    }

    @PostMapping("/department/{id}/chair")
    public ResponseEntity<List<Chair>> chair(
            @PathVariable("id") String id,
            @RequestBody List<ChairDTO> chairDTOs
    ) {
        return ResponseEntity.ok().body(
                clinicService.chair(
                        Long.parseLong(id),
                        chairDTOs
                )
        );
    }

    @PostMapping("/{id}/employee")
    public ResponseEntity<List<Employee>> employee(
            HttpServletRequest request,
            @PathVariable("id") String id,
            @RequestBody List<EmployeeDTO> employeeDTOs) {
        return ResponseEntity.ok(
                employeeService.employee(Long.parseLong(id), employeeDTOs)
        );
    }

    @PatchMapping("/department/{id}/schedule")
    public ResponseEntity<Schedule> schedule(
            @PathVariable("id") String id,
            @RequestBody Schedule schedule) {
        return new ResponseEntity<>(
                employeeService.schedule(Long.parseLong(id), schedule),
                HttpStatus.OK
        );
    }

}
