package datum.authenticate;

import datum.config.LogoutService;
import datum.authenticate.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final LogoutService logoutService;
    private final TokenService tokenService;

    @GetMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmail(
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(service.checkEmail(request));
    }

    @PostMapping("/register")
    public void register(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.register(request, response);
    }

    @GetMapping("/confirm")
    @ResponseBody
    public void confirm(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
       service.confirm(request, response);
    }

    @GetMapping("/authenticate")
    public void authenticate(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.authenticate(request, response);
    }

    @GetMapping("/resetPassword")
    public void resetPassword(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.resetPassword(request, response);
    }

    @GetMapping("/forgotPassword")
    public ResponseEntity<Integer> forgotPassword(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        return ResponseEntity.ok(service.forgotPassword(request, response));
    }

    @GetMapping("/resetPassword/cancel")
    public void cancelResetPassword(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.cancelResetPassword(request, response);
    }

    @GetMapping("/setPassword")
    public void setPassword(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.setPassword(request, response);
    }

    @GetMapping("/logout")
    public void logout(
            HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication
    ) {
        logoutService.logout(request, response, authentication);
    }
    @GetMapping("/logout/all")
    public void logoutAll(
            HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication
    ) {
        logoutService.logoutAll(request, response, authentication);
    }
    @GetMapping("/refreshToken")
    public void accessToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        tokenService.refreshToken(request, response);
    }
    @GetMapping("/changePassword")
    public void changePassword(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.changePassword(request, response);
    }
    @GetMapping("/validate")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        tokenService.validToken(request, response);
    }
}
