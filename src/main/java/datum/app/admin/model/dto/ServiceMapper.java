package datum.app.admin.model.dto;

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
