package datum.authenticate.user;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
//    Person convert(RegisterRequest registerRequest);
    Person convert(PersonDTO personDTO);
    @InheritInverseConfiguration
    PersonDTO convert(Person person);
}
