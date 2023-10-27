package datum.app.clinic.repositoy;

import datum.app.clinic.model.Anamnesis;
import datum.app.clinic.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AnamnesisRepository extends JpaRepository<Anamnesis, Long> {

    @Query(value = """
            SELECT *
            FROM anamnesis
            WHERE anamnesis.employee_id = :employeeId
            AND (anamnesis.deleted=false OR anamnesis.deleted IS NULL)
            """, nativeQuery = true)
    Optional<List<Anamnesis>> findAllByEmployeeId(long employeeId);

    @Query(value = """
            SELECT anamnesis.* FROM anamnesis
                INNER JOIN employee ON anamnesis.employee_id = employee.id
                INNER JOIN department ON employee.department_id = department.id
                INNER JOIN clinic ON department.clinic_id = clinic.id
            WHERE anamnesis.id = :anamnesisId
            AND anamnesis.employee_id=:employeeId
            AND (anamnesis.deleted=false OR anamnesis.deleted IS NULL)
            AND clinic.id = :clinicId
            AND (clinic.deleted = false OR clinic.deleted IS NULL)
            """, nativeQuery = true)
    Optional<List<Anamnesis>> findAllByClinicIdAndEmployeeId(long clinicId, long employeeId);

    @Query(value = """
            SELECT anamnesis.* FROM anamnesis
                INNER JOIN employee ON anamnesis.employee_id = employee.id
                INNER JOIN department ON employee.department_id = department.id
                INNER JOIN clinic ON department.clinic_id = clinic.id
            WHERE anamnesis.id = :anamnesisId
            AND anamnesis.employee_id=:employeeId
            AND (anamnesis.deleted=false OR anamnesis.deleted IS NULL)
            AND clinic.id = :clinicId AND (clinic.deleted = false OR clinic.deleted IS NULL)
            """, nativeQuery = true)
    Optional<Anamnesis> findByIdAndClinicIdAndEmployeeId(long anamnesisId, long clinicId, long employeeId);

    @Query(value = """
            SELECT anamnesis.* FROM anamnesis
                INNER JOIN employee ON anamnesis.employee_id = employee.id
                INNER JOIN department ON employee.department_id = department.id
                INNER JOIN clinic ON department.clinic_id = clinic.id
            WHERE clinic.id = :clinicId AND (clinic.deleted = false OR clinic.deleted IS NULL)
            AND anamnesis.id = :anamnesisId AND (anamnesis.deleted=false OR anamnesis.deleted IS NULL)
            """, nativeQuery = true)
    Optional<Anamnesis> findByIdAndClinicId(long anamnesisId, long clinicId);

    @Query(value = """
            SELECT CASE WHEN COUNT(*)> 0 THEN TRUE ELSE FALSE END
             FROM anamnesis
                INNER JOIN employee ON anamnesis.employee_id = employee.id
                INNER JOIN department ON employee.department_id = department.id
                INNER JOIN clinic ON department.clinic_id = clinic.id
            WHERE clinic.id = :clinicId AND (clinic.deleted = false OR clinic.deleted IS NULL)
            AND anamnesis.id = :anamnesisId AND (anamnesis.deleted=false OR anamnesis.deleted IS NULL)
            """, nativeQuery = true)
    boolean existsByIdAndClinicId(long anamnesisId, long clinicId);
}
