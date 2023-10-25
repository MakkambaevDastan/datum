package datum.app.clinic.repositoy;

import datum.app.clinic.model.Employee;
import datum.app.clinic.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = """
            SELECT *
            FROM employee
            WHERE employee.user_id = :userId
            """, nativeQuery = true)
Optional<List<Employee>> findAllByUserId(long userId);
    @Query(value = """
    SELECT
        CASE WHEN COUNT(*)> 0 THEN TRUE ELSE FALSE END
    FROM employee
        INNER JOIN department ON department.id = employee.department_id
    WHERE employee.id = :employeeId AND department.clinic_id = :clinicId
    """, nativeQuery = true)
    boolean existsByIdAndClinicId(long employeeId, long clinicId);
    @Query(value = """
            SELECT employee.* FROM employee INNER JOIN
                (SELECT DISTINCT employee.department_id
                FROM users INNER JOIN
                    employee ON users.id = employee.user_id
                WHERE users.id=:userId)
            AS department ON employee.department_id = department.department_id
            WHERE employee.id=:employeeId
            """, nativeQuery = true)
    Optional<Employee> findByIdExistsUserId(long employeeId, long userId);
    @Query(value = """
            SELECT employee.* FROM employee
            INNER JOIN department ON department.id = employee.department_id
                INNER JOIN (SELECT DISTINCT department.clinic_id AS id FROM employee
                            INNER JOIN department ON department.id = employee.department_id
                            WHERE employee.id=:employeeId) AS clinic ON department.clinic_id = clinic.id
            WHERE employee.id=:id
            """, nativeQuery = true)
    Optional<Employee> findByIdAndEmployeeId(long employeeId, long id);

    @Query(value = """
            SELECT employee.* FROM employee
                INNER JOIN department ON department.id = employee.department_id
            WHERE employee.id=:employeeId
            AND department.clinic_id=:clinicId
            """, nativeQuery = true)
    Optional<Employee> findByIdAndClinicId(long employeeId, long clinicId);
//    @Query(value = """
//            SELECT employee.* FROM employee
//                INNER JOIN department ON department.id = employee.department_id
//            WHERE employee.id=:employeeId
//            AND department.clinic_id=:clinicId
//            """, nativeQuery = true)
//    void setPostByIdAndClinicId(long post, long employeeId, long clinicId);
    @Query(value = """
            SELECT employee.* FROM employee
            INNER JOIN users  ON users.id = employee.user_id 
            WHERE users.id=:userId AND employee.id=:employeeId
            """, nativeQuery = true)
    Optional<Employee> getEmployeeByUserIdAndId(long userId, long employeeId);
    @Query(value = "SELECT * FROM employee WHERE employee.department_id=:departmentId", nativeQuery = true)
    Optional<List<Employee>> findAllByDepartmentId(long departmentId);
    @Query(value = """
    SELECT * FROM employee
    INNER JOIN department ON department.id = employee.department_id
    WHERE employee.department_id=:departmentId AND department.clinic_id=:clinicId
    """, nativeQuery = true)
    Optional<List<Employee>> findAllByDepartmentIdAndClinicId(long departmentId, long clinicId);
    @Query(value = """
    SELECT * FROM employee
    INNER JOIN department ON department.id = employee.department_id
    WHERE employee.id=:employeeId
    AND employee.department_id=:departmentId
    AND department.clinic_id=:clinicId
    """, nativeQuery = true)
    Optional<Employee> findByIdAndDepartmentIdAndClinicId(long employeeId, long departmentId, long clinicId);

    @Query(value = """
    SELECT * FROM employee
    WHERE employee.id=:employeeId
    AND employee.department_id=:departmentId
    """, nativeQuery = true)
    Optional<Employee> findByIdDepartmentId(long employeeId, long departmentId);
    @Query(value = """
                UPDATE employee
                SET employee.post_id=:postId
                WHERE employee.id=:employeeId
                AND EXISTS (SELECT * FROM post WHERE post.id=:postId)
            """, nativeQuery = true)
    void updatePost(long employeeId, long postId);
    @Query(value = """
                UPDATE employee
                SET employee.post_id=:postId
                WHERE employee.id=:employeeId
                AND EXISTS (SELECT * FROM post WHERE post.id=:postId)
                AND EXISTS
                (SELECT
                    CASE WHEN COUNT(*)> 0 THEN TRUE ELSE FALSE END
                FROM employee
                    INNER JOIN department ON department.id = employee.department_id
                WHERE employee.id = :employeeId AND department.clinic_id = :clinicId)
            """, nativeQuery = true)
    void updatePostByClinicId(long employeeId, long postId, long clinicId);
    @Query(value = """
                UPDATE employee
                SET employee.schedule=:schedule
                WHERE employee.id=:employeeId
            """, nativeQuery = true)
    void updateSchedule(long employeeId, Schedule schedule);

    @Query(value = """
                UPDATE employee
                SET employee.schedule=:schedule
                WHERE employee.id=:employeeId
                AND EXISTS (SELECT * FROM post WHERE post.id=:postId)
                AND EXISTS
                (SELECT
                    CASE WHEN COUNT(*)> 0 THEN TRUE ELSE FALSE END
                FROM employee
                    INNER JOIN department ON department.id = employee.department_id
                WHERE employee.id = :employeeId AND department.clinic_id = :clinicId)
            """, nativeQuery = true)
    void updateScheduleByClinicId(long employeeId, Schedule schedule, long clinicId);
}
