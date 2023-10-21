package datum.app.admin.mapping;

import datum.app.admin.dto.PersonDTO;
import datum.app.admin.model.Person;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
    Person convert(PersonDTO personDTO);
    @InheritInverseConfiguration
    PersonDTO convert(Person person);
}
