package datum.app.admin.service;

import datum.app.admin.model.Person;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PersonService {
    Person create(Person person);
    Person create(long clinicId,Person person);
    Person get(long personId);
    Page<Person> getPage(
            int page,
            int size,
            String field
    );
    void delete(long personId);
    List<Person> getAllByClinic(
            HttpServletRequest request,
            long clinicId,
            long employeeId
    );
    List<Person> getPageByClinic(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            int page,
            int size
    );
}
