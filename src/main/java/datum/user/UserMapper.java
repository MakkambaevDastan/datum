package datum.user;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@Mapper(uses = {PersonMapper.class,PasswordEncoderMapper.class},componentModel = "spring")
//@Mapper(uses = {PersonMapper.class,PasswordEncoderMapper.class})
@Mapper(uses = {PersonMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(source = "role", target = "role")
    @Mapping(source = "password", target = "password", qualifiedByName = "encryptPassword")
    @Mapping(source = "email", target = "email", qualifiedByName = "lowerCase")
    @Mapping(source = "person.email", target = "person.email", qualifiedByName = "lowerCase")
    UserDTO convert(User user);
    @Named("encryptPassword") // 2
    default String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
    @Named("lowerCase") // 2
    default String lowerCase(String email) {
        return email.toLowerCase();

    }
    @InheritInverseConfiguration
    User convert(UserDTO userDTO);
}
