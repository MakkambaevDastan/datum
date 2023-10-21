package datum.app.clinic.mapping;

import datum.app.clinic.dto.AppointmentDTO;
import datum.app.clinic.model.Appointment;
import datum.app.admin.repository.PersonRepository;
import datum.config.exception.ExceptionApp;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);
//    @Mapping(target="employee", source="employee", ignore = true)
    @Mapping(target="person", source="person", ignore = true)
    Appointment convert(
            AppointmentDTO appointmentDTO,
//            @Context EmployeeRepository employeeRepository,
            @Context PersonRepository personRepository
            );
    List<Appointment> convert(List<AppointmentDTO> appointmentDTOs,
                              @Context PersonRepository personRepository);
    @AfterMapping
    default void getEmployee(
            @MappingTarget final Appointment.AppointmentBuilder appointment,
            final AppointmentDTO appointmentDTO,
//            final @Context EmployeeRepository employeeRepository,
            final @Context PersonRepository personRepository
    ) {
//        appointment.employee(employeeRepository.findById(appointmentDTO.getEmployee()).orElseThrow());
        if(appointmentDTO.getPerson()!=null) {
            if (appointmentDTO.getPerson().getId() == 0) throw new ExceptionApp(400, "Пациент не указан");
            appointment.person(personRepository.findById(appointmentDTO.getPerson().getId()).orElseThrow());
        }
         else
             appointment.person(appointmentDTO.getPerson());
    }
}
