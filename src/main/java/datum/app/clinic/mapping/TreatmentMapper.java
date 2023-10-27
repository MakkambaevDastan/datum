package datum.app.clinic.mapping;

import datum.app.admin.dto.PersonDTO;
import datum.app.admin.model.Person;
import datum.app.clinic.dto.TreatmentDTO;
import datum.app.clinic.dto.TreatmentUpdate;
import datum.app.clinic.model.Treatment;
import datum.app.clinic.repositoy.PreiskurantRepository;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TreatmentMapper {

    TreatmentMapper INSTANCE = Mappers.getMapper(TreatmentMapper.class);
    @Mapping(target = "preiskurant", source = "preiskurant", ignore = true)
    Treatment convert(
            TreatmentDTO treatmentDTO,
            @Context PreiskurantRepository preiskurantRepository,
            @Context Long clinicId
            );
    @Mapping(target = "preiskurant", source = "preiskurant", ignore = true)
    List<Treatment> convert(List<TreatmentDTO> treatmentDTOs,
                            @Context PreiskurantRepository preiskurantRepository,
                            @Context Long clinicId);
    @AfterMapping
    default void getPreiskurant(
            final @MappingTarget Treatment.TreatmentBuilder treatment,
            final TreatmentDTO treatmentDTO,
            final @Context PreiskurantRepository preiskurantRepository,
            final @Context Long clinicId
    ){
        if (treatmentDTO.getPreiskurant() != null) {
            treatment.preiskurant(
                    preiskurantRepository.findByIdAndClinicId(
                            treatmentDTO.getPreiskurant(),
                            clinicId)
                    .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND))
            );
        }
    }
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(TreatmentUpdate treatmentUpdate, @MappingTarget Treatment treatment);
}
