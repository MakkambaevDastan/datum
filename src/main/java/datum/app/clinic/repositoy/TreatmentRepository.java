package datum.app.clinic.repositoy;

import datum.app.clinic.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    @Query(value = """
            SELECT treatment.*
            FROM treatment
                INNER JOIN anamnesis ON anamnesis.id = treatment.anamnesis_id
                    AND (anamnesis.deleted=false OR anamnesis.deleted IS NULL)
                INNER JOIN employee ON employee.id = anamnesis.employee_id
                    AND (employee.deleted=false OR employee.deleted IS NULL)
                INNER JOIN department ON department.id = employee.department_id
                    AND (department.deleted=false OR department.deleted IS NULL)
                INNER JOIN clinic ON clinic.id = department.clinic_id
                    AND (clinic.deleted=false OR clinic.deleted IS NULL)
            WHERE treatment.id=:treatmentId
                AND (treatment.deleted=false OR treatment.deleted IS NULL)
                AND clinic.id=:clinicId
            """, nativeQuery = true)
    Optional<Treatment> findByIdAndClinicId(Long treatmentId, Long clinicId);

    @Query(value = """
            SELECT treatment.*
            FROM treatment
                INNER JOIN anamnesis ON anamnesis.id = treatment.anamnesis_id 
                    AND (anamnesis.deleted=false OR anamnesis.deleted IS NULL)
                INNER JOIN employee ON anamnesis.employee_id = employee.id
                    AND (employee.deleted=false OR employee.deleted IS NULL)
                INNER JOIN department ON employee.department_id = department.id
                    AND (department.deleted=false OR department.deleted IS NULL)
                INNER JOIN clinic ON department.clinic_id = clinic.id
                    AND (clinic.deleted = false OR clinic.deleted IS NULL)
            WHERE clinic.id = :clinicId
                AND anamnesis.id = :anamnesisId
            """, nativeQuery = true)
    Optional<List<Treatment>> findAllByClinicIdAndAnamnesisId(long clinicId, long anamnesisId);
    @Query(value = """
            SELECT CASE WHEN COUNT(*)> 0 THEN TRUE ELSE FALSE END
            FROM treatment
                INNER JOIN anamnesis ON anamnesis.id = treatment.anamnesis_id
                    AND (anamnesis.deleted=false OR anamnesis.deleted IS NULL)
                INNER JOIN employee ON employee.id = anamnesis.employee_id
                    AND (employee.deleted=false OR employee.deleted IS NULL)
                INNER JOIN department ON department.id = employee.department_id
                    AND (department.deleted=false OR department.deleted IS NULL)
                INNER JOIN clinic ON clinic.id = department.clinic_id
                    AND (clinic.deleted=false OR clinic.deleted IS NULL)
            WHERE treatment.id=:treatmentId
                AND (treatment.deleted=false OR treatment.deleted IS NULL)
                AND clinic.id=:clinicId
            """, nativeQuery = true)
    boolean existsByIdAndClinicId(Long treatmentId, Long clinicId);
}
