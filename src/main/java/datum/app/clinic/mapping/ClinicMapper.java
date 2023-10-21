package datum.app.clinic.mapping;

import datum.app.clinic.dto.ClinicDTO;
import datum.app.clinic.model.Clinic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {DepartmentMapper.class})
public interface ClinicMapper {
    ClinicMapper INSTANCE = Mappers.getMapper(ClinicMapper.class);
    Clinic convert(ClinicDTO clinicDTO);
}
