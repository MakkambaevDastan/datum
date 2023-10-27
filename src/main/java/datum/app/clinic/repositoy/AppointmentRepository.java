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
            SELECT appointment.* FROM appointment
                INNER JOIN employee ON appointment.employee_id = employee.id
                INNER JOIN department ON employee.department_id = department.id
                INNER JOIN clinic ON department.clinic_id = clinic.id
            WHERE department.clinic_id = :clinicId
            AND (clinic.deleted = false OR clinic.deleted IS NULL)
            AND employee.id = :employeeId
            AND (appointment.deleted=false OR appointment.deleted IS NULL)
            """, nativeQuery = true)
    Optional<List<Appointment>> findAllByClinicIdAndEmployeeId(long clinicId, long employeeId);

    @Query(value = """ 
            SELECT appointment.* FROM appointment
                INNER JOIN employee ON appointment.employee_id = employee.id
                INNER JOIN department ON employee.department_id = department.id
                INNER JOIN clinic ON department.clinic_id = clinic.id
            WHERE department.clinic_id = :clinicId
            AND (clinic.deleted = false OR clinic.deleted IS NULL)
            AND appointment.id = :appointmentId
            AND (appointment.deleted=false OR appointment.deleted IS NULL)
            """, nativeQuery = true)
    Optional<Appointment> findByIdAndClinicId(long appointmentId, long clinicId);

    @Query(value = """ 
            SELECT appointment.* FROM appointment
                INNER JOIN employee ON appointment.employee_id = employee.id
                INNER JOIN department ON employee.department_id = department.id
                INNER JOIN clinic ON department.clinic_id = clinic.id
            WHERE department.clinic_id = :clinicId
            AND (clinic.deleted = false OR clinic.deleted IS NULL)
            AND employee.id = :employeeId
            AND appointment.id = :appointmentId
            AND (appointment.deleted=false OR appointment.deleted IS NULL)
            """, nativeQuery = true)
    Optional<Appointment> findByIdAndClinicIdAndEmployeeId(long appointmentId, long clinicId, long employeeId);

    @Query(value = """ 
            SELECT
                CASE WHEN COUNT(*)> 0 THEN TRUE ELSE FALSE END
            FROM appointment
                INNER JOIN employee ON appointment.employee_id = employee.id
                INNER JOIN department ON employee.department_id = department.id
                INNER JOIN clinic ON department.clinic_id = clinic.id
            WHERE department.clinic_id = :clinicId
            AND (clinic.deleted = false OR clinic.deleted IS NULL)
            AND appointment.id = :appointmentId
            AND (appointment.deleted=false OR appointment.deleted IS NULL)
            """, nativeQuery = true)
    boolean existsByIdAndClinicId(long appointmentId, long clinicId);

}
