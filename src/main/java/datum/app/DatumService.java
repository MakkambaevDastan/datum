package datum.app;

import datum.authenticate.user.PersonDTO;

public interface DatumService {
    Object post();

    Object endpoint();

    Object day();

    Object person(PersonDTO personDTO);
    Object person(Integer page, Integer size, String field);
    Object person(Long id, Integer page, Integer size);

    Object icd10();

    Object service();
}
