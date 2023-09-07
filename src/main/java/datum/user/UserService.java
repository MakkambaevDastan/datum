package datum.user;

//import com.example.demo.registration.token.ConfirmationToken;
//import com.example.demo.registration.token.ConfirmationTokenService;

import datum.token.ConfirmationToken;
import datum.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final ConfirmationTokenService confirmationTokenService;
    private final UserRepository userRepository;
//
//    public String signUpUser(User user) {
//        String token = UUID.randomUUID().toString();
//
//        var confirmationToken = ConfirmationToken
//                .builder()
//                .token(token)
//                .createdAt(LocalDateTime.now())
//                .expiresAt(LocalDateTime.now().plusMinutes(15))
//                .build();
//        confirmationToken.setUser(user);
//        confirmationTokenService.saveConfirmationToken(confirmationToken);
//        return token;
//    }

//    public int enableUser(String email) {
//        return userRepository.enableUser(email);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}