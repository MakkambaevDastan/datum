package datum.app.clinic.model.dto;

import datum.app.admin.model.dto.PostMapper;
import datum.app.clinic.model.Employee;
import datum.app.admin.model.repository.PostRepository;
import datum.authenticate.user.UserMapper;
import datum.config.exception.ExceptionApp;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {UserMapper.class, PostMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(source = "post", target = "post", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "deleted", constant = "false")
    Employee convert(EmployeeDTO employeeDTO, @Context PostRepository postRepository);

    @AfterMapping
    default void getPost(
            @MappingTarget final Employee.EmployeeBuilder employee,
            final EmployeeDTO employeeDTO,
            final @Context PostRepository postRepository
    ) {
        var post = postRepository.findByCode(employeeDTO.getPost());
        if(post.isPresent())
            employee.post(post.get());
        else throw new ExceptionApp(400, "Не правильний пост");
    }

    List<Employee> list(List<EmployeeDTO> employee, @Context PostRepository postRepository);
}
