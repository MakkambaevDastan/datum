package datum.app.admin.mapping;

import datum.app.admin.dto.ICD10DTO;
import datum.app.admin.model.ICD10;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ICD10Mapper {
    ICD10Mapper INSTANCE = Mappers.getMapper(ICD10Mapper.class);
    ICD10 convert(ICD10DTO icd10DTO);
    List<ICD10> convert(List<ICD10DTO> icd10DTOs);
}
