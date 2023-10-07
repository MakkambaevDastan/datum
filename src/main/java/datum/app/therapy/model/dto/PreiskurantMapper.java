package datum.app.therapy.model.dto;

import datum.app.therapy.model.Preiskurant;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PreiskurantMapper {
    PreiskurantMapper INSTANCE = Mappers.getMapper(PreiskurantMapper.class);

//    @Mapping(target = "service", source = "service", ignore = true)
//    @Mapping(target = "icd10", source = "icd10", ignore = true)
    Preiskurant convert(
            PreiskurantDTO preiskurantDTO
//            @Context ServiceRepository serviceRepository,
//            @Context ICD10Repository icd10Repository
    );
    List<Preiskurant> list(List<PreiskurantDTO> preiskurantDTO);

//    @AfterMapping
//    default void getService(
//            @MappingTarget final Preiskurant.PreiskurantBuilder preiskurant,
//            final PreiskurantDTO preiskurantDTO,
//            final @Context ServiceRepository serviceRepository,
//            final @Context ICD10Repository icd10Repository
//    ) {
//        preiskurant.service(serviceRepository.findById(preiskurantDTO.getService()).orElseThrow());
//        preiskurant.icd10(preiskurantDTO.getIcd10().stream()
//                .map(id -> icd10Repository.findById(id).orElseThrow())
//                .toList());
//    }
}
