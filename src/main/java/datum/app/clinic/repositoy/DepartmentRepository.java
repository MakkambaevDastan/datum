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
               INNER JOIN employee ON department.id =employee.department_id
                    AND (employee.deleted=false OR employee.deleted IS NULL)
           WHERE department.id=:departmentId
           AND (department.deleted=false OR department.deleted IS NULL)
           AND employee.id=:employeeId
           """, nativeQuery = true)
    Optional<Department> findByIdAndEmployeeId(long departmentId, long employeeId);
    @Query(value = """
                 SELECT * FROM department INNER JOIN
                     (SELECT DISTINCT clinic.id FROM clinic
                        INNER JOIN department ON department.clinic_id= clinic_id
                            AND department.id=:departmentId
                            AND (department.deleted=false OR department.deleted IS NULL)
                        INNER JOIN employee ON department.id =employee.department_id
                            AND (employee.deleted=false OR employee.deleted IS NULL)
                        INNER JOIN users ON employee.user_id = users.id
                            AND (users.deleted=false OR users.deleted IS NULL)
                            AND users.id = :userId
                        INNER JOIN post ON employee.post_id = post.id
                            AND (post.deleted=false OR post.deleted IS NULL)
                            AND post.code='OWNER'
                        WHERE (clinic.deleted=false OR clinic.deleted IS NULL))
                    AS clinic ON clinic.id = department.clinic_id
                    WHERE department.id = :departmentId
            """, nativeQuery = true)
    Optional<Department> findByIdAndOwnerId(long departmentId, long userId);
//    @Query(value = """
//            SELECT department.*
//            FROM department
//                INNER JOIN clinic ON clinic.id=department.clinic_id
//            WHERE clinic.user_id=:userId AND department.clinic_id=:clinicId
//        """, nativeQuery = true)
//    Optional<List<Department>> findAllByClinicIdAndUserId(long clinicId, long userId);
    @Query(value = """
            SELECT *
            FROM department
            WHERE department.id=:departmentId
                AND department.clinic_id=:clinicId
                AND (department.deleted=false OR department.deleted IS NULL)
        """, nativeQuery = true)
    Optional <Department> findByIdAndClinicId(long departmentId ,long clinicId);
    @Query(value = """
            SELECT CASE WHEN COUNT(*)> 0 THEN TRUE ELSE FALSE END
            FROM department
            WHERE department.id=:departmentId
                AND department.clinic_id=:clinicId
            AND (department.deleted=false OR department.deleted IS NULL)
        """, nativeQuery = true)
    boolean existsByIdAndClinicId(long departmentId ,long clinicId);
    @Query(value = """
    SELECT * FROM department WHERE clinic_id = :clinicId
    AND (department.deleted=false OR department.deleted IS NULL)
    """, nativeQuery = true)
    Optional<List<Department>> findAllByClinicId(long clinicId);

//    Optional<Department> findByClinicIdAndName();

}
