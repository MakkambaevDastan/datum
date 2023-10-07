package datum.app;

import datum.authenticate.user.PersonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/datum")
public class DatumController {
    private final DatumService datumService;

    @GetMapping("/get")
    public ResponseEntity<Object> get() {
        return ResponseEntity.ok().body(
                List.of(datumService.post(),
                        datumService.endpoint(),
                        datumService.day()));
    }
    @PostMapping("/person")
    public ResponseEntity<Object> person(@RequestBody PersonDTO personDTO){
        return ResponseEntity.ok(datumService.person(personDTO));
    }
    @GetMapping("/person/{field}/{page}/{size}")
    public ResponseEntity<Object> person(
            @PathVariable("page") String page,
            @PathVariable("size") String size,
            @PathVariable("field") String field
    ){
        return ResponseEntity.ok(datumService.person(Integer.parseInt(page), Integer.parseInt(size), field));
    }
    @GetMapping("/{id}/person/{page}/{size}")
    public ResponseEntity<Object> personClinic(
            @PathVariable("id") String id,
            @PathVariable("page") String page,
            @PathVariable("size") String size
    ){
        return ResponseEntity.ok(datumService.person(Long.parseLong(id), Integer.parseInt(page), Integer.parseInt(size)));
    }
    @GetMapping("/icd10")
    public ResponseEntity<Object> getIcd10() {
        return ResponseEntity.ok().body( datumService.icd10());
    }
    @GetMapping("/service")
    public ResponseEntity<Object> getService() {
        return ResponseEntity.ok().body(datumService.service());
    }
}
