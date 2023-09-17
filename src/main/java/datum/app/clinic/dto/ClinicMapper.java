package datum.app.clinic.dto;

import datum.app.clinic.model.Clinic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {DepartmentMapper.class})
public interface ClinicMapper {
    ClinicMapper INSTANCE = Mappers.getMapper(ClinicMapper.class);
//    ClinicDTO convert(Clinic cLinic);
//    @InheritInverseConfiguration
//    @Mapping(source = "departments", target = "department")
@Mapping(target = "enabled", constant = "true")
@Mapping(target = "deleted", constant = "false")
    Clinic convert(ClinicDTO clinicDTO);
}
