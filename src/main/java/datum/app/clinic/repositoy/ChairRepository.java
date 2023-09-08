package datum.app.clinic.repositoy;

import datum.app.clinic.model.Chair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChairRepository extends JpaRepository<Chair, Long> {
}
