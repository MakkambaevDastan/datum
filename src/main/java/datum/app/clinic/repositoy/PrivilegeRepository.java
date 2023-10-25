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
        SELECT privilege.bool
        FROM privilege
            INNER JOIN post ON post.id = privilege.post_id
            INNER JOIN employee ON employee.post_id = post.id
        WHERE privilege.clinic_id = :clinicId
            AND privilege.method = :method
            AND privilege.endpoint = :endpoint
            AND employee.id = :employeeId
    """, nativeQuery = true)
    Optional<Boolean> getPrivilegeById(
            Long clinicId,
            String method,
            String endpoint,
            Long employeeId
    );

}
