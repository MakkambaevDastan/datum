package datum.app.clinic.controller;

import datum.app.clinic.dto.*;
import datum.app.clinic.model.*;
import datum.app.clinic.service.ClinicService;
import datum.app.clinic.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clinic")
public class ClinicController {
    private final ClinicService clinicService;
    private final EmployeeService employeeService;

    @PostMapping(value = "/clinic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Clinic>> clinic(
            @RequestBody ClinicDTO clinicDTO
    ) {
        return ResponseEntity.ok(clinicService.clinic(clinicDTO));
    }
    @GetMapping(value = "/clinic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Clinic>> clinic() {
        return ResponseEntity.ok(clinicService.clinic());
    }

    @GetMapping("/{id}/privilege")
    public ResponseEntity<List<Privilege>> privilege(@PathVariable("id") String id){
        return ResponseEntity.ok(clinicService.privilege(Long.parseLong(id)));
    }
    @PatchMapping("/clinic/privilege")
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

    @PostMapping("/clinic/{id}/room")
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

    @PostMapping("/clinic/department/{id}/chair")
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

    @PostMapping("/clinic/{id}/employee")
    public ResponseEntity<List<Employee>> employee(
            HttpServletRequest request,
            @PathVariable("id") String id,
            @RequestBody List<EmployeeDTO> employeeDTOs) {
        return ResponseEntity.ok(
                employeeService.employee(request, Long.parseLong(id), employeeDTOs)
        );
    }

    @PostMapping("/clinic/department/{id}/schedule")
    public ResponseEntity<Employee> schedule(
            @PathVariable("id") String id,
            @RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.ok(
                employeeService.schedule(Long.parseLong(id), scheduleDTO)
        );
    }
}
