package datum.app.clinic.model.repositoy;

import datum.app.clinic.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
//    @Query(value = "SELECT * FROM employee INNER JOIN " +
//                   "    (SELECT DISTINCT employee.department_id " +
//                   "    FROM users INNER JOIN " +
//                   "        employee ON users.id = employee.user_id " +
//                   "    WHERE users.id=:userId)" +
//                   "AS department ON employee.department_id = department.department_id " +
//                   "WHERE employee.id=:employeeId",
//            nativeQuery = true)
    @Query(value = """
            SELECT * FROM employee INNER JOIN  
                (SELECT DISTINCT employee.department_id 
                FROM users INNER JOIN 
                    employee ON users.id = employee.user_id 
                WHERE users.id=:userId)
            AS department ON employee.department_id = department.department_id 
            WHERE employee.id=:employeeId
            """,
            nativeQuery = true)
    Optional<Employee> getEmployeeWhereUserIdAndEmployeeId(Long userId, Long employeeId);
    @Query(value = """
            SELECT employee.* FROM employee 
            INNER JOIN users  ON users.id = employee.user_id 
            WHERE users.id=:userId AND employee.id=:employeeId
            """,
            nativeQuery = true)
    Optional<Employee> getEmployeeByUserIdAndId(Long userId, Long employeeId);
}
