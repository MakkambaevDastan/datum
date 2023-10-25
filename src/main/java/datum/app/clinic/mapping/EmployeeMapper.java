package datum.app.clinic.mapping;

import datum.app.admin.mapping.PostMapper;
import datum.app.clinic.dto.EmployeeDTO;
import datum.app.clinic.model.Employee;
import datum.app.admin.repository.PostRepository;
import datum.authenticate.UserMapper;
import datum.config.exception.ExceptionApp;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {UserMapper.class, PostMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(source = "post", target = "post", ignore = true)
    Employee convert(EmployeeDTO employeeDTO, @Context PostRepository postRepository);

    @AfterMapping
    default void getPost(
            @MappingTarget final Employee.EmployeeBuilder employee,
            final EmployeeDTO employeeDTO,
            final @Context PostRepository postRepository
    ) {
        employee.post(postRepository.findByCode(employeeDTO.getPost())
                .orElseThrow(() -> new ExceptionApp(400, "Не правильний пост"))
        );
    }

    List<Employee> convert(List<EmployeeDTO> employee, @Context PostRepository postRepository);
}
