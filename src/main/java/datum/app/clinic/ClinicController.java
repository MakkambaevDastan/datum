package datum.app.clinic;

import datum.app.clinic.dto.*;
import datum.app.clinic.model.Chair;
import datum.app.clinic.model.Day;
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
public class ClinicController {
    private final ClinicService clinicService;
    private final EmployeeService employeeService;
    @PostMapping(value = "clinic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> clinic(
            @RequestBody List<ClinicDTO> clinicDTOs
    ) {
        return ResponseEntity.ok(clinicService.clinic(clinicDTOs));
    }
    @PostMapping("/{id}/department")
    public ResponseEntity<Object> department(
            @PathVariable("id") String id,
            @RequestBody List<DepartmentDTO> departmentDTOs
    ) {
        return ResponseEntity.ok(
                clinicService.department(Long.parseLong(id), departmentDTOs)
        );
    }
    @PostMapping("clinic/{id}/room")
    public ResponseEntity<Object> room(
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
    @PostMapping("clinic/department/{id}/chair")
    public ResponseEntity<List<Chair> > chair(
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
    @PostMapping("clinic/{id}/employee")
    public ResponseEntity<Object> employee(
            HttpServletRequest request,
            @PathVariable("id") String id,
            @RequestBody List<EmployeeDTO> employeeDTOs){
        return ResponseEntity.ok(
                employeeService.employee(request, Long.parseLong(id), employeeDTOs)
        );
    }
    @PostMapping("clinic/department/{id}/schedule")
    public ResponseEntity<Object> schedule(
            @PathVariable("id") String id,
            @RequestBody ScheduleDTO scheduleDTO){
        return ResponseEntity.ok(
                employeeService.schedule(Long.parseLong(id), scheduleDTO)
        );
    }
}
