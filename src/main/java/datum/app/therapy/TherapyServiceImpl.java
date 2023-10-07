package datum.app.therapy;

import datum.Main;
import datum.app.admin.model.repository.ICD10Repository;
import datum.app.admin.model.repository.ServiceRepository;
import datum.app.clinic.model.repositoy.ClinicRepository;
import datum.app.clinic.model.repositoy.EmployeeRepository;
import datum.app.therapy.model.Anamnesis;
import datum.app.therapy.model.Appointment;
import datum.app.therapy.model.Preiskurant;
import datum.app.therapy.model.dto.*;
import datum.app.therapy.model.repository.*;
import datum.authenticate.user.PersonRepository;
import datum.config.exception.ExceptionApp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TherapyServiceImpl implements TherapyService {
    private final ServiceRepository serviceRepository;
    private final ICD10Repository icd10Repository;
    private final PreiskurantRepository preiskurantRepository;
    private final ClinicRepository clinicRepository;
    private final EmployeeRepository employeeRepository;
    private final PersonRepository personRepository;
    private final AppointmentRepository appointmentRepository;
    private final AnamnesisRepository anamnesisRepository;

    @Override
    public Object preiskurant(
            HttpServletRequest request,
            HttpServletResponse response,
            Long id,
            List<PreiskurantDTO> preiskurantDTOs
    ) {
        var userId = Main.getUserId();
        var clinic = clinicRepository.selectWhereIdAndUserId(id, userId.get(), "OWNER");
        if (clinic.isPresent()) {
            List<Preiskurant> preiskurants = PreiskurantMapper.INSTANCE.list(preiskurantDTOs);
            clinic.get().getPreiskurants().addAll(preiskurants);
            clinicRepository.save(clinic.get());
            return preiskurants;
        }
        throw new ExceptionApp(404, "Клиника не найден");

    }

    @Override
    public Object appointment(
            HttpServletRequest request,
            HttpServletResponse response,
            AppointmentDTO appointmentDTO
    ) {
        var userId = Main.getUserId();
        if (userId.isPresent()) {
            var employeeId = appointmentDTO.getEmployee();
            if (employeeId == 0) throw new ExceptionApp(400, "Персонал не указан");
            var employee = employeeRepository.getEmployeeWhereUserIdAndEmployeeId(userId.get(), employeeId);
            System.out.println(userId.get() + " " + employeeId);
            if (employee.isPresent()) {
                Appointment appointment = AppointmentMapper.INSTANCE.convert(appointmentDTO, personRepository);
                employee.get().getAppointments().add(appointment);
                employeeRepository.save(employee.get());
                return appointment;
            }
            throw new ExceptionApp(404, "Персонал не найден");
        }
        throw new ExceptionApp(404, "Ваша учетная запись не найдена");
    }

    @Override
    public Object anamnesis(
            HttpServletRequest request,
            HttpServletResponse response,
            AnamnesisDTO anamnesisDTO
    ) {
        var userId = Main.getUserId();
        var employeeId = anamnesisDTO.getEmployee();
        var employee = employeeRepository.getEmployeeWhereUserIdAndEmployeeId(userId.get(), employeeId);
        if (employee.isPresent()) {
            Anamnesis anamnesis = AnamnesisMapper.INSTANCE.convert(anamnesisDTO, icd10Repository);
//            anamnesisRepository.save(anamnesis);
            var person = personRepository.findById(anamnesisDTO.getPerson());
            if (person.isPresent()) {
//                if (person.get().getAnamnesis().isEmpty())
//                    person.get().setAnamnesis(List.of(anamnesis));
//                else
//                    person.get().getAnamnesis().add(anamnesis);
//                personRepository.save(person.get());
                anamnesis.setPerson(person.get());
                anamnesisRepository.save(anamnesis);
            }
//            if (employee.get().getAnamnesis().isEmpty())
//                employee.get().setAnamnesis(List.of(anamnesis));
//            else
                employee.get().getAnamnesis().add(anamnesis);
            employeeRepository.save(employee.get());
            return anamnesis;
        }
        throw new ExceptionApp(404, "Персонал не найден");
    }


}
