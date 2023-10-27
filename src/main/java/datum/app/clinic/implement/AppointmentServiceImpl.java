package datum.app.clinic.implement;

import datum.Main;
import datum.app.clinic.dto.AppointmentDTO;
import datum.app.clinic.mapping.AppointmentMapper;
import datum.app.clinic.model.Appointment;
import datum.app.clinic.model.Employee;
import datum.app.clinic.repositoy.AppointmentRepository;
import datum.app.clinic.repositoy.EmployeeRepository;
import datum.app.clinic.service.AppointmentService;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Appointment> get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id
    ) {
        return appointmentRepository.findAllByClinicIdAndEmployeeId(clinicId,id)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
    }

    @Override
    public Appointment get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long appointmentId
    ) {
        return appointmentRepository.findByIdAndClinicId(appointmentId, clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
    }

    @Override
    public Appointment create(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            Appointment appointment
    ) {
        Employee employee = employeeRepository.findByIdAndClinicId(id, clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
        appointment.setEmployee(employee);
        return appointmentRepository.save(appointment);
    }

//    @Override
//    public Appointment update(
//            HttpServletRequest request,
//            long clinicId,
//            long employeeId,
//            long departmentId,
//            long id,
//            long appointmentId,
//            AppointmentDTO appointmentDTO
//    ) {
//        Appointment appointment = appointmentRepository.findByIdAndClinicId(appointmentId, clinicId)
//                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
//        AppointmentMapper.INSTANCE.update(appointmentDTO, appointment);
//        appointmentRepository.save(appointment);
//        return appointment;
//    }

    @Override
    public void delete(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long appointmentId
    ) {
        if (!appointmentRepository.existsByIdAndClinicId(appointmentId, clinicId))
            throw new ExceptionApp(404, Message.NOT_FOUND);
        appointmentRepository.deleteById(appointmentId);
    }
}
