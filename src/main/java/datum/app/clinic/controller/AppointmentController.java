package datum.app.clinic.controller;

import datum.Main;
import datum.app.clinic.dto.AppointmentDTO;
import datum.app.clinic.mapping.AppointmentMapper;
import datum.app.clinic.model.Appointment;
import datum.app.clinic.service.AppointmentService;
import datum.app.admin.repository.PersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/CLINIC/{clinicId}/employee/{employeeId}/department/{departmentId}/employee/{id}/appointment")
public class AppointmentController {
    private final PersonRepository personRepository;
    private final AppointmentService appointmentService;
    @GetMapping
    public ResponseEntity<List<Appointment>> getAppointment(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id
    ) {
        return ResponseEntity.ok(
                appointmentService.get(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        Main.parseLong(id)
                )
        );

    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> getAppointment(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id,
            @PathVariable("appointmentId") String appointmentId
    ) {
        return ResponseEntity.ok(
                appointmentService.get(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        Main.parseLong(id),
                        Main.parseLong(appointmentId)
                )
        );
    }
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id,
            @RequestBody AppointmentDTO appointmentDTO
    ) {
        return ResponseEntity.ok(
                appointmentService.create(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        Main.parseLong(id),
                        AppointmentMapper.INSTANCE.convert(appointmentDTO, personRepository)

                )
        );
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<Appointment> appointment(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id,
            @PathVariable("appointmentId") String appointmentId,
            @RequestBody AppointmentDTO appointmentDTO
    ) {
        return ResponseEntity.ok(
                appointmentService.update(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        Main.parseLong(id),
                        Main.parseLong(appointmentId),
                        AppointmentMapper.INSTANCE.convert(appointmentDTO, personRepository)
                )
        );
    }

    @DeleteMapping("/{appointmentId}")
    public void deleteAppointment(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id,
            @PathVariable("appointmentId") String appointmentId
    ) {
        appointmentService.delete(
                request,
                Main.parseLong(clinicId),
                Main.parseLong(employeeId),
                Main.parseLong(departmentId),
                Main.parseLong(id),
                Main.parseLong(appointmentId)
        );
    }

}
