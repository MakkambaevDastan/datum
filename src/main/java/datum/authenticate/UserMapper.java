package datum.authenticate;

import datum.app.admin.mapping.PersonMapper;
import datum.config.exception.ExceptionApp;
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
//    @Mapping(source = "role", target = "role", qualifiedByName = "toRole")
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "deleted", constant = "false")
    User convert(UserDTO userDTO);

    @Named("encryptPassword")
    default String encryptPassword(String password) {
        if (password==null) throw new ExceptionApp(400, "Введите пароль");
        if (password.equals("") || password.equals(" ")) throw new ExceptionApp(400,"Пароль не может быть пустым");
        if (password.contains(" ")) throw new ExceptionApp(400,"Пароль содержить пробел");
        return new BCryptPasswordEncoder().encode(password);
    }

    @Named("lowerCase")
    default String lowerCase(String email) {

        return email.toLowerCase().trim();
    }
    @Named("toRole")
    default Role toRole(String role) {
        return Role.valueOf(role);
    }

    @InheritInverseConfiguration
    UserDTO convert(User user);
}
