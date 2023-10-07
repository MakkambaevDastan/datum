package datum.config;

import datum.config.exception.ExceptionApp;
import datum.authenticate.token.Token;
import datum.authenticate.token.TokenRepository;
import datum.authenticate.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final TokenService tokenService;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    )  {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new ExceptionApp(401, "Ошибка авторизации");

        final String jwt = authHeader.substring(7);
        final Optional<Token> storedToken = tokenRepository.findByToken(jwt);
        if (storedToken.isPresent()) {
            tokenRepository.delete(storedToken.get());
            SecurityContextHolder.clearContext();
        } else throw new ExceptionApp(500, "Токен не найден");
    }

    public void logoutAll(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new ExceptionApp(401, "Ошибка авторизации");
        final String jwt = authHeader.substring(7);
        tokenService.deleteAllUserToken(jwt);
    }
}
