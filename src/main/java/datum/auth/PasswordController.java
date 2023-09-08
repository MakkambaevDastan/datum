package datum.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordController {
    private final AuthenticationService service;

    @PostMapping("/changePassword")
    public ResponseEntity<Boolean> changePassword(HttpServletRequest request,
                                                  @RequestBody ChangePasswordRequest changePasswordRequest){
        return ResponseEntity.ok(service.changePassword(request, changePasswordRequest));
    }
}
