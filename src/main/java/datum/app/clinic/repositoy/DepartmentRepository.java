package datum.app.clinic.repositoy;

import datum.app.clinic.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query(value = """
           SELECT department.*
           FROM department
               INNER JOIN clinic ON department.clinic_id = clinic.id
               INNER JOIN users ON clinic.user_id = users.id
               INNER JOIN employee ON users.id = employee.user_id
               INNER JOIN post ON post.id = employee.post_id
           WHERE department.id=:departmentId AND users.id=:userId AND post.code=:post
           """, nativeQuery = true)
    Optional<Department> getWhereIdAndUserId(Long departmentId, Long userId, String post);
    @Query(value = """
            SELECT department.*
            FROM department
                INNER JOIN clinic ON clinic.id=department.clinic_id
            WHERE clinic.user_id=:userId AND department.clinic_id=:clinicId
        """, nativeQuery = true)
    Optional<List<Department>> findAllByClinicIdAndUserId(long clinicId, long userId);
    @Query(value = """
            SELECT *
            FROM department
            WHERE department.id=:departmentId
                AND department.clinic_id=:clinicId
        """, nativeQuery = true)
    Optional <Department> findByIdAndClinicId(long departmentId ,long clinicId);
    @Query(value = """
            SELECT CASE WHEN COUNT(*)> 0 THEN TRUE ELSE FALSE END
            FROM department
            WHERE department.id=:departmentId
                AND department.clinic_id=:clinicId
        """, nativeQuery = true)
    boolean existsByIdAndClinicId(long departmentId ,long clinicId);
    @Query(value = "SELECT * FROM department WHERE clinic_id = :clinicId", nativeQuery = true)
    Optional<List<Department>> findAllByClinicId(long clinicId);

}
