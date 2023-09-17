package datum.app.clinic.dto;

import datum.app.clinic.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {RoomMapper.class})
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "deleted", constant = "false")
    Department convert(DepartmentDTO departmentDTO);

}
