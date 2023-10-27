package datum.app.clinic.mapping;

import datum.app.clinic.dto.AppointmentDTO;
import datum.app.clinic.model.Appointment;
import datum.app.admin.repository.PersonRepository;
import datum.app.clinic.model.Treatment;
import datum.app.clinic.repositoy.TreatmentRepository;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    //    @Mapping(target="employee", source="employee", ignore = true)
    @Mapping(target = "person", source = "person", ignore = true)
    @Mapping(target = "treatments", source = "treatments", ignore = true)
    Appointment convert(
            AppointmentDTO appointmentDTO,
            @Context PersonRepository personRepository,
            @Context TreatmentRepository treatmentRepository,
            @Context long clinicId
    );

    List<Appointment> convert(List<AppointmentDTO> appointmentDTOs,
                              @Context PersonRepository personRepository,
                              @Context TreatmentRepository treatmentRepository,
                              @Context long clinicId
    );

    @AfterMapping
    default void getEmployee(
            @MappingTarget final Appointment.AppointmentBuilder appointment,
            final AppointmentDTO appointmentDTO,
            final @Context PersonRepository personRepository,
            final @Context TreatmentRepository treatmentRepository,
            final @Context long clinicId
    ) {
        if (appointmentDTO.getPerson() != null) {
            appointment.person(
                    personRepository.findById(appointmentDTO.getPerson())
                    .orElse(null)
            );
        }
        if (!appointmentDTO.getTreatments().isEmpty()) {
            List<Treatment> treatments = new ArrayList<>();
            appointmentDTO.getTreatments().forEach(
                    treatmentId ->
                            treatments.add(
                                    treatmentRepository.findByIdAndClinicId(treatmentId, clinicId)
                                    .orElseThrow(() ->
                                            new ExceptionApp(404, Message.NOT_FOUND)
                                    )
                            )
            );
            appointment.treatments(treatments);
        }
    }
}
