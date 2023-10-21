package datum.authenticate.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import datum.config.JwtService;
import datum.config.exception.ExceptionApp;
import datum.authenticate.User;
import datum.authenticate.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    public void deleteAllUserToken(String refreshToken){
        var user = userRepository.findByEmailIgnoreCase(jwtService.extractEmail(refreshToken));
        deleteAllUserTokens(user.get());
    }

    public void deleteAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        tokenRepository.deleteAll(validUserTokens);
    }

    public void saveUserToken(User user, String refreshToken) {
        var token = Token.builder()
                .user(user)
                .token(refreshToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }


    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        Optional<Token> token = getUserFromValidToken(request);
        if (token.isPresent()) {
            response.setStatus(200);
            response.addHeader(HttpHeaders.AUTHORIZATION, jwtService.generateToken(token.get().getUser()));
            new ObjectMapper().writeValue(response.getOutputStream(), true);
        } else {
            response.setStatus(400);
            new ObjectMapper().writeValue(response.getOutputStream(), false);
        }
    }

    public void validToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        var opt = getUserFromValidToken(request);
        boolean b;
        if(opt.isPresent()) {
            b = true;
            response.setStatus(200);
        } else{
            b = false;
            response.setStatus(404);
        }
        new ObjectMapper().writeValue(response.getOutputStream(), b);
    }

    public Optional<Token> getUserFromValidToken(HttpServletRequest request) {
        final String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearer == null || !bearer.startsWith("Bearer ")) throw new ExceptionApp(401, "Нет доступа");
        final String refreshToken = bearer.substring(7);
        final String email = jwtService.extractEmail(refreshToken);
        if (jwtService.isTokenValid(refreshToken, email))
            return tokenRepository.findByToken(refreshToken);
        final Optional<Token> storedToken = tokenRepository.findByToken(refreshToken);
        tokenRepository.delete(storedToken.get());
        SecurityContextHolder.clearContext();
        return storedToken;
    }
}
