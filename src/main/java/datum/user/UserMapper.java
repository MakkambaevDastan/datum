package datum.user;

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
//    @Mapping(source = "person.email", target = "person.email", qualifiedByName = "lowerCase")
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "deleted", constant = "false")
    User convert(UserDTO userDTO);

//    @Mapping(source = "password", target = "password", qualifiedByName = "encryptPassword")
//    @Mapping(source = "email", target = "email", qualifiedByName = "lowerCase")
////    @Mapping(source = "person.email", target = "person.email", qualifiedByName = "lowerCase")
//    User convert(RegisterRequest registerRequest);

    @Named("encryptPassword") // 2
    default String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
    @Named("lowerCase") // 2
    default String lowerCase(String email) {
        return email.toLowerCase();
    }
    @InheritInverseConfiguration
    UserDTO convert(User user);
}
