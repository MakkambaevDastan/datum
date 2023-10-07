package datum.authenticate.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor

public class PersonServiceImplements implements PersonService{
    private final PersonRepository personRepository;

    @Override
    public Person person(PersonDTO personDTO) {
        LocalDate birthDay = null;
            var person = Person.builder()
                    .firstname(personDTO.getFirstname())
                    .surname(personDTO.getSurname())
                    .patronymic(personDTO.getPatronymic())
                    .address(personDTO.getAddress())
                    .birthDay(birthDay)
                    .phone(personDTO.getPhone())
                    .email(personDTO.getEmail())
                    .build();
            personRepository.save(person);
            return person;
    }
}
