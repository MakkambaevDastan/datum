package datum.app.clinic.model.repositoy;

import datum.app.clinic.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
}
