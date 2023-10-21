//package datum.token;
//
//import datum.auth.AuthenticationService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
////@RestController
////@RequestMapping("/api/v1/token")
////@RequiredArgsConstructor
//public class TokenController {
////    private final AuthenticationService authenticationService;
//
//    @GetMapping("/refreshToken")
//    public void accessToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        authenticationService.refreshToken(request, response);
//    }
//
//    @GetMapping("/validate")
//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        authenticationService.validToken(request, response);
//    }
//}
