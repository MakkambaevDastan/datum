package datum.app.admin.repository;


import datum.app.admin.model.Person;
import org.springframework.data.domain.Pageable;
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
                        INNER JOIN clinic ON department.clinic_id = clinic.id
                    WHERE clinic.id = :clinicId AND (clinic.deleted = false OR clinic.deleted IS NULL)
                    ORDER BY person.firstname ASC
                    LIMIT :size OFFSET (:page * :size)
                    """, nativeQuery = true)
    List<Person> getPageByClinic(long clinicId, int page, int size);

    @Query(value =
            """
                    SELECT person.* FROM person
                        INNER JOIN anamnesis ON person.id = anamnesis.person_id
                        INNER JOIN employee ON employee.id = anamnesis.employee_id
                        INNER JOIN department ON employee.department_id = department.id
                        INNER JOIN clinic ON department.clinic_id = clinic.id
                    WHERE clinic.id = :clinicId AND (clinic.deleted = false OR clinic.deleted IS NULL)
                    """, nativeQuery = true)
    Optional<List<Person>> findAllByClinic(long clinicId);

    @Query(value = """
            SELECT person.* FROM person
            INNER JOIN clinic_person ON person.id = clinic_person.person_id
            WHERE person.id = :personId
            AND (person.deleted=false OR person.deleted IS NULL)
            AND clinic_person.clinic_id = :clinicId
            AND EXISTS (SELECT * FROM clinic
                WHERE clinic.id=:clinicId AND (clinic.deleted=false OR clinic.deleted IS NULL))
            """, nativeQuery = true)
    Optional<Person> findByIdAndClinicId(Long personId, Long clinicId);
}
