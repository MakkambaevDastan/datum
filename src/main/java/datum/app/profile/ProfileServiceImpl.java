package datum.app.profile;

import datum.authenticate.user.Person;
import datum.authenticate.user.PersonDTO;
import datum.authenticate.user.PersonMapper;
import datum.authenticate.user.UserRepository;
import datum.config.exception.ExceptionApp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    @Override
    public Object person(PersonDTO personDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByEmailIgnoreCase(email);
        if (user.isPresent()) {
            Person person = PersonMapper.INSTANCE.convert(personDTO);
            user.get().setPerson(person);
            userRepository.save(user.get());
            return user.get().getPerson();
        }
        throw new ExceptionApp(404, "Пользователь не найден");
    }
}
