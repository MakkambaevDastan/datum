package datum.app.clinic.mapping;

import datum.app.clinic.dto.DepartmentDTO;
import datum.app.clinic.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);
    Department convert(DepartmentDTO departmentDTO);
    List<Department> convert(List<DepartmentDTO> departmentDTOs);



}
