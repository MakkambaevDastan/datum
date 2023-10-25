package datum.app.clinic.implement;

import datum.Main;
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
        if(!employeeRepository.existsByIdAndClinicId(id,clinicId))
            throw new ExceptionApp(404, Message.NOT_FOUND);
        return appointmentRepository.findAllByEmployeeId(id)
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
        return appointmentRepository.findById(appointmentId)
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
        Employee empl = employeeRepository.findByIdAndClinicId(id, clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
        employeeRepository.save(empl);
        return appointment;
    }

    @Override
    public Appointment update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long appointmentId,
            Appointment appointment
    ) {
        Appointment appointment1 = appointmentRepository.findByIdAndClinicId(appointmentId, clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
        appointment1.setDescription(appointment.getDescription());
        appointment1.setVisible(appointment.getVisible());
        appointment1.setPerson(appointment.getPerson());
        appointment1.setStart(appointment.getStart());
        appointment1.setMinute(appointment.getMinute());
        appointmentRepository.save(appointment1);
        return appointment1;
    }

    @Override
    public void delete(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long appointmentId
    ) {
        if(!appointmentRepository.existsByIdAndClinicId(appointmentId, clinicId))
            throw new ExceptionApp(404, Message.NOT_FOUND );
        appointmentRepository.deleteById(appointmentId);
    }
}
