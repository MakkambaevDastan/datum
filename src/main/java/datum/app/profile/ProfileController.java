package datum.app.profile;

import datum.authenticate.user.PersonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;
    @PostMapping("/person")
    public ResponseEntity<Object> person(@RequestBody PersonDTO personDTO){
        return ResponseEntity.ok(profileService.person(personDTO));
    }

}
