package datum.app.admin.mapping;

import datum.app.admin.dto.ServiceDTO;
import datum.app.admin.model.Service;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ServiceMapper {
    ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);
    Service convert(ServiceDTO serviceDTO);
   List<Service> convert (List<ServiceDTO> serviceDTO);
}
