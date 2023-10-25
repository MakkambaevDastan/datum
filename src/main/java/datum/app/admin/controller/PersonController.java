package datum.app.admin.controller;

import datum.Main;
import datum.app.admin.dto.PersonDTO;
import datum.app.admin.mapping.PersonMapper;
import datum.app.admin.model.Person;
import datum.app.admin.service.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ADMIN/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    @GetMapping({ "/{personId}"})
    public ResponseEntity<Person> get(
            @PathVariable("personId") String personId
    ) {
        return ResponseEntity.ok(personService.get(Main.parseLong(personId)));
    }

    @GetMapping("/{field}/{page}/{size}")
    public ResponseEntity<Object> person(
            @PathVariable("page") String page,
            @PathVariable("size") String size,
            @PathVariable("field") String field
    ){
        return ResponseEntity.ok(
                personService.getPage(
                        Main.parseInt(page),
                        Main.parseInt(size),
                        field
                )
        );
    }

    @PostMapping
    public ResponseEntity<Person> create(
            @RequestBody PersonDTO personDTO
    ){
        return ResponseEntity.ok(
                personService.create(
                        PersonMapper.INSTANCE.convert(personDTO)
                )
        );
    }

    @DeleteMapping("/{personId}")
    public void person(@PathVariable String personId){
        personService.delete(
                Main.parseLong(personId)
        );
    }

}
