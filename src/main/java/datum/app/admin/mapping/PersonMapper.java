package datum.app.admin.mapping;

import datum.app.admin.dto.PersonDTO;
import datum.app.admin.model.Person;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
    Person convert(PersonDTO personDTO);
    List<Person> convert(List<PersonDTO> personDTOs);
//    @InheritInverseConfiguration
//    PersonDTO convert(Person person);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(PersonDTO personDTO, @MappingTarget Person person);



}
