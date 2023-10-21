package datum.app.clinic.repositoy;

import datum.app.clinic.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query(value = "SELECT * FROM appointment WHERE appointment.employee_id = :employeeId", nativeQuery = true)
    Optional<List<Appointment>> findAllByEmployeeId(long employeeId);
    @Query(value = """
    SELECT * FROM (SELECT DISTINCT employee.department_id FROM employee WHERE employee.id = :employeeId)
    AS dep INNER JOIN employee ON dep.department_id = employee.department_id
    INNER JOIN appointment ON dep.department_id = appointment.department_id
    WHERE appointment.id = :appointmentId
    """, nativeQuery = true)
    Optional<Appointment> findByIdExistsEmployeeId(long appointmentId, long employeeId);
    @Query(value = """ 
    SELECT appointment.* FROM appointment
        INNER JOIN employee ON appointment.employee_id = employee.id
        INNER JOIN department ON employee.department_id = department.id
    WHERE department.clinic_id = :clinicId AND appointment.id = :appointmentId
    """, nativeQuery = true)
    Optional<Appointment> findByIdAndClinicId(long appointmentId, long clinicId);
    @Query(value = """ 
    SELECT
        CASE WHEN COUNT(*)> 0 THEN TRUE ELSE FALSE END
    FROM appointment
        INNER JOIN employee ON appointment.employee_id = employee.id
        INNER JOIN department ON employee.department_id = department.id
    WHERE department.clinic_id = :clinicId AND appointment.id = :appointmentId
    """, nativeQuery = true)
    boolean existsByIdAndClinicId(long appointmentId, long clinicId);

}
