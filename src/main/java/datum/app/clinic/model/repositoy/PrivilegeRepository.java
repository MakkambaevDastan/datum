package datum.app.clinic.model.repositoy;

import datum.app.clinic.model.Privilege;
import datum.app.clinic.model.PrivilegeID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrivilegeRepository extends JpaRepository<Privilege, PrivilegeID> {
    @Query("select p from Privilege p where p.clinicId = :id")
    List<Privilege> getPrivilegeFromClinicId(Long id);
}
