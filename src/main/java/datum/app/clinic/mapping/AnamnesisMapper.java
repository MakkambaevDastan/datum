package datum.app.clinic.mapping;

import datum.app.admin.repository.PersonRepository;
import datum.app.clinic.dto.AnamnesisDTO;
import datum.app.clinic.model.Anamnesis;
import datum.app.admin.model.ICD10;
import datum.app.admin.repository.ICD10Repository;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(uses={TreatmentMapper.class})
public interface AnamnesisMapper {
    AnamnesisMapper INSTANCE = Mappers.getMapper(AnamnesisMapper.class);

    @Mapping(target = "clinicalDiagnosis", source = "clinicalDiagnosis", ignore = true)
    @Mapping(target = "person", source = "person", ignore = true)
    Anamnesis convert(
            AnamnesisDTO anamnesisDTO,
            @Context ICD10Repository icd10Repository,
            @Context PersonRepository personRepository
    );
    List<Anamnesis> convert(
            List<AnamnesisDTO> anamnesisDTOs,
            @Context ICD10Repository icd10Repository,
            @Context PersonRepository personRepository
    );
    @AfterMapping
    default void getAppointment(
            final @MappingTarget Anamnesis.AnamnesisBuilder anamnesis,
            final AnamnesisDTO anamnesisDTO,
            final @Context ICD10Repository icd10Repository,
            final @Context PersonRepository personRepository
    ) {
        anamnesis.person(personRepository.findById(anamnesisDTO.getPerson())
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND)));
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
