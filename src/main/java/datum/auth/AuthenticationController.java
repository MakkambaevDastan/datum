package datum.auth;

import datum.config.LogoutService;
import datum.user.User;
import datum.user.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final LogoutService logoutService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            HttpServletRequest headRequest,
            @RequestBody UserDTO userDTO
    ) {
        try {
            return ResponseEntity.ok(service.register(headRequest, userDTO));
        } catch (IllegalStateException e) {
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
            @RequestBody Email email
    ) {
        return ResponseEntity.ok(service.checkEmail(email.getEmail()));
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
    public ResponseEntity<String> resetPassword(
            HttpServletRequest request,
            @RequestBody Email email) {
        return ResponseEntity.ok(service.resetPassword(request, email.getEmail()));
    }

    @GetMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestParam("id") Long id, @RequestParam("token") String token) {
        return ResponseEntity.ok(service.forgotPassword(id, token));
    }

    @PostMapping("/setPassword")
    public ResponseEntity<Boolean> setPassword(@RequestBody ForgotPassword forgotPassword) {
        return ResponseEntity.ok(service.setPassword(forgotPassword));
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        logoutService.logout(request, response, authentication);
    }
}
