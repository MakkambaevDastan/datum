package datum.authenticate.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query(value =
        """
        SELECT person.* FROM person
        INNER JOIN anamnesis ON person.id = anamnesis.person_id
        INNER JOIN employee ON employee.id = anamnesis.employee_id
        INNER JOIN department ON employee.department_id = department.id
        WHERE department.clinic_id = :id
        ORDER BY person.firstname ASC 
        LIMIT :size OFFSET (:page * :size);
        """,
            nativeQuery = true)
    List<Person> findAll(Long id, Integer page, Integer size);
}
