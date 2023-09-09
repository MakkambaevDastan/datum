package datum.user;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
//    @Mapping(source = "email", target = "email", qualifiedByName = "lowerCase")
    PersonDTO convert(Person person);
//    @Named("lowerCase") // 2
//    default String lowerCase(String email) {
//        return email.toLowerCase();
//
//    }
    @InheritInverseConfiguration
    Person convert(PersonDTO personDTO);
}
