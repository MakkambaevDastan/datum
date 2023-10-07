package datum.app.admin.model.dto;

import datum.app.admin.model.ICD10;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ICD10Mapper {
    ICD10Mapper INSTANCE = Mappers.getMapper(ICD10Mapper.class);
    ICD10 convert(ICD10DTO icd10DTO);
}
