package datum.app.clinic.model.repositoy;

import datum.app.clinic.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query(value = "SELECT " +
                   "department.*" +
                   " FROM department " +
                   "INNER JOIN clinic ON department.clinic_id = clinic.id " +
                   "INNER JOIN users ON clinic.user_id = users.id " +
                   "INNER JOIN employee ON users.id = employee.user_id " +
                   "INNER JOIN post ON post.id = employee.post_id " +
                   "WHERE department.id=:id AND users.id=:userId AND post.code=:post",
            nativeQuery = true)
    Optional<Department> getWhereIdAndUserId(Long id, Long userId, String post);
}
