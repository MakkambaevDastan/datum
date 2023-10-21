package datum.app.clinic.service;

import datum.app.clinic.model.Appointment;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AppointmentService {
    List<Appointment> get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id
    );
    Appointment get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long appointmentId
    );

    Appointment create(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            Appointment appointments
    );
    Appointment update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long appointmentId,
            Appointment appointment
    );
    void delete(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long appointmentId
    );

}
