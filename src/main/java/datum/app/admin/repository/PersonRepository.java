package datum.app.admin.repository;


import datum.app.admin.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value =
            """
            SELECT person.* FROM person
                INNER JOIN anamnesis ON person.id = anamnesis.person_id
                INNER JOIN employee ON employee.id = anamnesis.employee_id
                INNER JOIN department ON employee.department_id = department.id
            WHERE department.clinic_id = :clinicId
            ORDER BY person.firstname ASC
            LIMIT :size OFFSET (:page * :size);
            """, nativeQuery = true)
    Optional<List<Person>> findAll(long clinicId, int page, int size);
    @Query(value =
            """
            SELECT person.* FROM person
                INNER JOIN anamnesis ON person.id = anamnesis.person_id
                INNER JOIN employee ON employee.id = anamnesis.employee_id
                INNER JOIN department ON employee.department_id = department.id
            WHERE department.clinic_id = :clinicId
            """, nativeQuery = true)
    Optional<List<Person>> findAll(long clinicId);
    @Query(value =
            """
            SELECT person.* FROM person
            INNER JOIN users ON person.id = users.person_id
            WHERE users.id = :userId
            AND person.id = :personId
            """, nativeQuery = true)
    Optional<Person> findByIdAndUserId(long personId, long userId);

//    Optional<Person> findByName(String dastan);
}
