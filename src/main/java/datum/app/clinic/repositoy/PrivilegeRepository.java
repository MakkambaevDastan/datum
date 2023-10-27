package datum.app.clinic.repositoy;

import datum.app.clinic.model.Privilege;
import datum.app.clinic.model.PrivilegeID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, PrivilegeID> {
    @Query("select p from Privilege p where p.clinicId = :id")
    Optional<List<Privilege>> getPrivilegeFromClinicId(Long id);
    @Query(value = """
            SELECT (CASE WHEN count(*)>0 OR
                        (SELECT CASE WHEN post.code='OWNER' THEN true ELSE false END
                            FROM employee INNER JOIN post ON post.id=employee.post_id
                            AND employee.id = :employeeId
                            WHERE (employee.deleted=false OR employee.deleted IS NULL))
                    THEN true ELSE false END)
            FROM privilege
                INNER JOIN employee ON employee.post_id = privilege.post_id
                    AND employee.id = :employeeId AND (employee.deleted = false OR employee.deleted IS NULL)
                INNER JOIN 	post ON post.id = privilege.post_id
                INNER JOIN clinic ON clinic.id = privilege.clinic_id
            WHERE privilege.clinic_id = :clinicId
            AND privilege.method = :method
            AND privilege.endpoint = :endpoint
            AND (clinic.deleted=false OR clinic.deleted IS NULL)
            """, nativeQuery = true)
    Optional<Boolean> findById(
            Long clinicId,
            Long employeeId,
            String endpoint,
            String method
    );

}
