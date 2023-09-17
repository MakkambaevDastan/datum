package datum.app.clinic.model.repositoy;

import datum.app.clinic.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
