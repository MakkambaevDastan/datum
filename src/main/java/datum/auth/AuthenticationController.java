package datum.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            HttpServletRequest headRequest,
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(headRequest, request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmail(
            @RequestBody EmailRequest request
    ) {
        return ResponseEntity.ok(service.checkEmail(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @GetMapping(path = "/confirm")
    public ResponseEntity<AuthenticationResponse> confirm(@RequestParam("token") String token) {
            return ResponseEntity.ok(service.confirmToken(token));
    }
//    @PostMapping("/resetPassword")
//    public ResponseEntity<Boolean> resetPassword(HttpServletRequest request,
//                                                 @RequestBody UsernameRequest username) {
//        return ResponseEntity.ok(service.resetPassword(request, username));
//    }

}
