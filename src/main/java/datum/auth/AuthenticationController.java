package datum.auth;

import datum.email.EmailService;
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
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            HttpServletRequest headRequest,
            @RequestBody RegisterRequest request
    ) {
        try{
            return ResponseEntity.ok(service.register(headRequest, request));
        } catch (IllegalStateException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

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
        return ResponseEntity.ok(service.checkEmail(request.getEmail()));
    }

    @GetMapping("/refresh-token")
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

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(HttpServletRequest request,
                                                @RequestBody EmailRequest email) {
        return ResponseEntity.ok(service.resetPassword(request, email));
    }

    @GetMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestParam("id") Long id, @RequestParam("token") String token) {
        return ResponseEntity.ok(service.forgotPassword(id, token));
    }

    @PostMapping("/setNewPassword")
    public  ResponseEntity<Boolean> setNewPassword(HttpServletRequest request, @RequestBody NewPasswordRequest newPassword) {
        return ResponseEntity.ok(service.setNewPassword(request, newPassword));
    }

}
