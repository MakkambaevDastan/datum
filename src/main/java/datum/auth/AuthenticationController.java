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
    public ResponseEntity<User> register(
            HttpServletRequest headRequest,
            @RequestBody UserDTO userDTO
    ) {
        return ResponseEntity.ok(service.register(headRequest, userDTO));
    }

    @GetMapping("/cancel/{token}")
    public ResponseEntity<Object> cancel(@PathVariable("token") String token) {
        return ResponseEntity.ok(service.cancel(token));
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

    @GetMapping("/accessToken")
    public void accessToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.accessToken(request, response);
    }

    @GetMapping("/refreshToken")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @GetMapping("/refreshToken/validate")
    public ResponseEntity<Object> refreshToken(
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(service.refreshToken(request));
    }

    @GetMapping(path = "/confirm/{token}")
    public ResponseEntity<AuthenticationResponse> confirm(@PathVariable("token") String token) throws Exception {
        return ResponseEntity.ok(service.confirm(token));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(
            HttpServletRequest request,
            @RequestBody Email email) {
        return ResponseEntity.ok(service.resetPassword(request, email.getEmail()));
    }

    @GetMapping("/forgotPassword/{token}")
    public ResponseEntity<String> forgotPassword(@PathVariable("token") String token) {
        return ResponseEntity.ok(service.forgotPassword(token));
    }

    @GetMapping("/forgotPassword/cancel/{token}")
    public ResponseEntity<AuthenticationResponse> forgotPasswordCancel(@PathVariable("token") String token) {
        return ResponseEntity.ok(service.forgotPasswordCancel(token));
    }

    @PostMapping("/setPassword")
    public ResponseEntity<AuthenticationResponse> setPassword(@RequestBody ForgotPassword forgotPassword) {
        return ResponseEntity.ok(service.setPassword(forgotPassword));
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        logoutService.logout(request, response, authentication);
    }
}
