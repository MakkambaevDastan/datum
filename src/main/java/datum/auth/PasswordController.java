package datum.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordController {
    private final AuthenticationService service;

    @PostMapping("/change")
    public ResponseEntity<Boolean> changePassword(@RequestBody Password password) {
        return ResponseEntity.ok(service.changePassword(password));
    }


}
