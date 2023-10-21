package datum.app.clinic.controller;

import datum.Main;
import datum.app.admin.model.Person;
import datum.app.admin.service.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/CLINIC/{clinicId}/EMPLOYEE/{employeeId}")
public class ClinicController {

    private final PersonService personService;

    @GetMapping("/person")
    public ResponseEntity<List<Person>> getAllByClinic(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId
    ) {
        return ResponseEntity.ok(
                personService.getAllByClinic(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId)
                )
        );
    }
    @GetMapping("/person/{page}/{size}")
    public ResponseEntity<List<Person>> getPageByClinic(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("page") String page,
            @PathVariable("size") String size
    ) {
        return ResponseEntity.ok(
                personService.getPageByClinic(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseInt(page),
                        Main.parseInt(size)
                )
        );
    }
    @RequestMapping(value = "/person", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<Person> updatePerson(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @RequestBody Person person
    ) {
        return ResponseEntity.ok(
                personService.create(Main.parseLong(clinicId),person)
        );
    }
    //=================================================================================================



    //=================================================================================================
    //=================================================================================================
    //=================================================================================================
    //=================================================================================================
}
