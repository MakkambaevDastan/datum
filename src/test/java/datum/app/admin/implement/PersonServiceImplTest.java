//package datum.app.admin.implement;
//
//import datum.app.admin.model.Person;
//import datum.app.admin.repository.PersonRepository;
//import datum.app.clinic.model.Clinic;
//import datum.app.clinic.repositoy.ClinicRepository;
//import datum.config.exception.ExceptionApp;
//import datum.config.exception.Message;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//@ExtendWith(MockitoExtension.class)
//class PersonServiceImplTest {
//    @Mock
//    private PersonRepository personRepository;
//    @Mock
//    private ClinicRepository clinicRepository;
//
//    @InjectMocks
//    private PersonServiceImpl personService;
//    Person person = Person.builder()
//            .id(1L)
//            .firstname("Dastan")
//
//            .build();
//Clinic clinic = Clinic.builder()
//        .id(1L)
//        .name("Datum")
//        .persons(new ArrayList<>())
//        .build();
//    @Test
//    void test_Get() {
//        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));
//        Person result = personService.get(1L);
//        assertEquals("Dastan", result.getFirstname());
//    }
//    @Test
//    void test_GetThrow() {
//        Mockito.when(personRepository.findById(10L)).thenReturn(Optional.empty());
//        ExceptionApp exception = assertThrows(
//                ExceptionApp.class,
//                () -> personService.get(10L)
//        );
//        assertEquals(404, exception.getCode());
//        assertEquals(Message.NOT_FOUND, exception.getMessage());
//    }
//    @Test
//    void test_Create() {
//        Mockito.when(personRepository.save(person)).thenReturn(person);
//        Person result = personService.create(person);
//        assertEquals("Dastan", result.getFirstname());
//    }
//    @Test
//    void test_CreateByClinic() {
//        Mockito.when(clinicRepository.findById(1L)).thenReturn(Optional.ofNullable(clinic));
//        Person result = personService.createByClinic(1L, person);
//        assertEquals("Dastan", result.getFirstname());
//    }
//
//
//}