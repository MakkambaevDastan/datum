package datum.app.user;

import datum.app.admin.model.Person;
import datum.app.admin.dto.PersonDTO;
import datum.app.admin.mapping.PersonMapper;
import datum.authenticate.UserRepository;
import datum.config.exception.ExceptionApp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
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
