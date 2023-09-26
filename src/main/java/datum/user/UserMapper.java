package datum.user;

import datum.exception.BadRequest;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(uses = {PersonMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "password", target = "password", qualifiedByName = "encryptPassword")
    @Mapping(source = "email", target = "email", qualifiedByName = "lowerCase")
    @Mapping(source = "role", target = "role")
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "deleted", constant = "false")
    User convert(UserDTO userDTO);

    @Named("encryptPassword") // 2
    default String encryptPassword(String password) {
        if (password==null) throw new BadRequest("Введите пароль");
        if (password.equals("") || password.equals(" ")) throw new BadRequest("Пароль не может быть пустым");
        if (password.contains(" ")) throw new BadRequest("Пароль содержить пробел");
        return new BCryptPasswordEncoder().encode(password);
    }

    @Named("lowerCase") // 2
    default String lowerCase(String email) {
        return email.toLowerCase();
    }

    @InheritInverseConfiguration
    UserDTO convert(User user);
}
