package datum.app.clinic.mapping;

import datum.app.clinic.dto.AnamnesisDTO;
import datum.app.clinic.model.Anamnesis;
import datum.app.admin.model.ICD10;
import datum.app.admin.repository.ICD10Repository;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AnamnesisMapper {
    AnamnesisMapper INSTANCE = Mappers.getMapper(AnamnesisMapper.class);

    @Mapping(target = "clinicalDiagnosis", source = "clinicalDiagnosis", ignore = true)
//    @Mapping(target = "person", source = "person", ignore = true)
    Anamnesis convert(
            AnamnesisDTO anamnesisDTO,
            @Context ICD10Repository icd10Repository
    );
    List<Anamnesis> convert(
            List<AnamnesisDTO> anamnesisDTOs,
            @Context ICD10Repository icd10Repository
    );
    @AfterMapping
    default void getAppointment(
            final @MappingTarget Anamnesis.AnamnesisBuilder anamnesis,
            final AnamnesisDTO anamnesisDTO,
            final @Context ICD10Repository icd10Repository
    ) {
        List<ICD10> icd = new ArrayList<>();
        anamnesisDTO.getClinicalDiagnosis().forEach(
                icd10 -> {
                    if(icd10!=null && icd10>0)
                        icd.add(icd10Repository.findById(icd10).orElseThrow());
                }
        );
        anamnesis.clinicalDiagnosis(icd);
    }
}
