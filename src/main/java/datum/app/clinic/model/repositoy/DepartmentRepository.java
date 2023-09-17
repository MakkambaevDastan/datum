package datum.app.clinic.model.repositoy;

import datum.app.clinic.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
