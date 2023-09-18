package datum.app.clinic.dto;

import datum.app.clinic.model.Day;
import datum.app.clinic.model.Employee;
import datum.app.clinic.model.Post;
import datum.app.clinic.model.repositoy.PostRepository;
import datum.user.UserMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
//@Mapper(componentModel="spring")
//public interface FilesDTOMapper {
//    @Mapping(target = "xyz", ignore = true)
//    @Mapping(target = "issue", source = "domain.issueNumber")
//    @Mapping(target = "DOI", source = "domain.doi")
//    FilesDTO map( BackHalfDomain domain, @Context MyRepo repo);
//    @AfterMapping
//    default void map( @MappingTarget FilesDTO target, BackHalfDomain domain, @Context MyRepo repo) {
//        target.setXYZ(repo.findById(domain.getId()));
//    }
//}
@Mapper(uses = {UserMapper.class, PostMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(source = "post", target = "post", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "start", source = "start", qualifiedByName = "toMap")
    @Mapping(target = "end", source = "end", qualifiedByName = "toMap")
    Employee convert(EmployeeDTO employeeDTO, @Context PostRepository postRepository);

    @AfterMapping
    default void getPost(@MappingTarget final Employee.EmployeeBuilder employee,
                         final EmployeeDTO employeeDTO,
                         final @Context PostRepository postRepository) {
        employee.post(postRepository.findById(employeeDTO.getPost()).orElseThrow());
    }

    @Named("toMap")
    default Map<Day, LocalTime> toMap(Map<String, String> source) {
        Map<Day, LocalTime> map = new EnumMap<>(Day.class);
        source.forEach((k, v) -> map.put(Day.valueOf(k), LocalTime.parse(v)));
        return map;
    }
//    @Mapping(qualifiedByName="toMap")
//    @Mapping(target = "end", source = "end", qualifiedByName="toMap")
//    Map<Day, LocalTime> schedule(Map<String, String> source);
//    @InheritInverseConfiguration
//    @Mapping(source = "role", target = "role")
//    @Mapping(target = "start", source = "start", qualifiedByName="map")
//    @Mapping(target = "end", source = "end", qualifiedByName="map")
//    EmployeeDTO convert(Employee employee);

    List<Employee> list(List<EmployeeDTO> employee, @Context PostRepository postRepository);

//    @EnumMapping(nameTransformationStrategy = "suffix", configuration = "_TYPE")

}
