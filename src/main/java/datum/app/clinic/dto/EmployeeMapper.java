package datum.app.clinic.dto;

import datum.app.clinic.model.Day;
import datum.app.clinic.model.Employee;
import datum.user.UserMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Mapper(uses = {UserMapper.class})
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(source = "role", target = "role")
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "start", source = "start", qualifiedByName="toMap")
    @Mapping(target = "end", source = "end", qualifiedByName="toMap")
//    @Mapping(target = "start", source = "start", dateFormat="HH:mm:ss")
//    @Mapping(target = "end", source = "end", dateFormat="HH:mm:ss")
    Employee convert(EmployeeDTO employeeDTO);
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

    List<Employee> list(List<EmployeeDTO> employee);

//    @EnumMapping(nameTransformationStrategy = "suffix", configuration = "_TYPE")

}
