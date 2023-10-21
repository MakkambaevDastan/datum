package datum.app.user;

import datum.Main;
import datum.app.clinic.dto.ClinicDTO;
import datum.app.clinic.mapping.ClinicMapper;
import datum.app.clinic.model.Clinic;
import datum.app.clinic.model.Employee;
import datum.app.clinic.service.ClinicService;
import datum.app.clinic.service.EmployeeService;
import datum.app.admin.dto.PersonDTO;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/USER")
public class UserController {
    private final UserService profileService;
    private final EmployeeService employeeService;
    private final ClinicService clinicService;
    @GetMapping("/clinic")
    public ResponseEntity<List<Clinic>> getClinicAll() {
        return ResponseEntity.ok(clinicService.get());
    }

    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<Clinic> getClinic(
            @PathVariable("clinicId") String clinicId
    ) {
        if (Main.isLong(clinicId))
            return ResponseEntity.ok(clinicService.get(Long.parseLong(clinicId)));
        throw new ExceptionApp(400, Message.INVALID_ID);
    }

    @PostMapping("/clinic")
    public ResponseEntity<Clinic> createClinic(
            @RequestBody ClinicDTO clinicDTO
    ) {
        var clinic = ClinicMapper.INSTANCE.convert(clinicDTO);
        return ResponseEntity.ok(clinicService.create(clinic));
    }

    @PutMapping("/clinic/{clinicId}")
    public ResponseEntity<String> updateClinic(
            @PathVariable("clinicId") String clinicId,
            @RequestBody String name
    ) {
        if (Main.isLong(clinicId))
            return ResponseEntity.ok(clinicService.update(Long.parseLong(clinicId), name));
        throw new ExceptionApp(400, Message.INVALID_ID);
    }

    @DeleteMapping("/clinic/{clinicId}")
    public void deleteClinic(
            @PathVariable("clinicId") String clinicId
    ) {
        if (Main.isLong(clinicId))
            clinicService.delete(Long.parseLong(clinicId));
        throw new ExceptionApp(400, Message.INVALID_ID);
    }
    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> employee(){
        return ResponseEntity.ok(
                employeeService.employee()
        );
    }

    @PostMapping("/person")
    public ResponseEntity<Object> person(@RequestBody PersonDTO personDTO){
        return ResponseEntity.ok(profileService.person(personDTO));
    }

}
