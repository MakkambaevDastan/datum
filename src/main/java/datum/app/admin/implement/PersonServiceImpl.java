package datum.app.admin.implement;

import datum.app.admin.dto.PersonDTO;
import datum.app.admin.mapping.PersonMapper;
import datum.app.admin.model.Person;
import datum.app.admin.repository.PersonRepository;
import datum.app.admin.service.PersonService;
import datum.app.clinic.repositoy.ClinicRepository;
import datum.authenticate.User;
import datum.authenticate.UserRepository;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;

    @Override
    public Person get(long personId) {
        var person = personRepository.findById(personId);
        if (person.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return person.get();
    }

    @Override
    public Person create(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person createByUser(long userId, PersonDTO personDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
        if (user.getPerson() != null) PersonMapper.INSTANCE.update(personDTO, user.getPerson());
        else user.setPerson(PersonMapper.INSTANCE.convert(personDTO));
//        phoneRepository.deleteAll(user.getPerson().getPhone());
//        user.getPerson().setPhone(PhoneMapper.INSTANCE.convert(personDTO.getPhone()));
        userRepository.save(user);
        return user.getPerson();
    }

    @Override
    public Person createByClinic(long clinicId, Person person) {
        var clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
        clinic.addPerson(person);
        clinicRepository.save(clinic);
        return person;
    }

    @Override
    public Page<Person> getPage(
            int page,
            int size,
            String field
    ) {
        return personRepository.findAll(PageRequest.of(page, size, Sort.by(field)));
    }


    @Override
    public List<Person> getAllByClinic(
            HttpServletRequest request,
            long clinicId,
            long employeeId
    ) {
        var person = personRepository.findAll(clinicId);
        if (person.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return person.get();
    }


    @Override
    public List<Person> getPageByClinic(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            int page,
            int size
    ) {
        var person = personRepository.findAll(clinicId, page, size);
        if (person.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return person.get();
    }


    @Override
    public void delete(long personId) {
        personRepository.deleteById(personId);
    }

    private Map<String, Object> convertToResponse(final Page<Person> pagePersons) {
        Map<String, Object> response = new HashMap<>();
        response.put("persons", pagePersons.getContent());
        response.put("current-page", pagePersons.getNumber());
        response.put("total-items", pagePersons.getTotalElements());
        response.put("total-pages", pagePersons.getTotalPages());
        return response;
    }
}
