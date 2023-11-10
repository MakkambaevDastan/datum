//package datum.app.admin.implement;
//
//import datum.app.admin.model.Endpoint;
//import datum.app.admin.repository.EndpointRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class EndpointServiceImplTest {
//    @Mock
//    private EndpointRepository endpointRepository;
//    @InjectMocks
//    private EndpointServiceImpl endpointService;
//            Endpoint endpoint1=   Endpoint.builder()
//                        .endpoint("/CLINIC/{clinicId}/employee/{employeeId}")
//                    .method("GET")
//                    .name("/CLINIC/{clinicId}/employee/{employeeId}")
//                    .build();
//    Endpoint endpoint2=        Endpoint.builder()
//                                .endpoint("/CLINIC/{clinicId}")
//                    .method("GET")
//                    .name("/CLINIC/{clinicId}")
//                    .build();
//    List<Endpoint> endpoints = List.of( endpoint1, endpoint2    );
//
//    @Test
//    void test_get() {
//        Mockito.when(endpointRepository.findAll()).thenReturn(endpoints);
//        List<Endpoint> result = endpointService.get();
//        assertEquals(2, result.size());
//        assertTrue(result.get(0).getEndpoint().contains("/CLINIC/{clinicId}/employee/{employeeId}"));
//    }
//
//    @Test
//    void test_Create() {
//        Mockito.when(endpointRepository.saveAll(endpoints)).thenReturn(endpoints);
//        Endpoint result = endpointService.create(endpoint1);
//        assertTrue(result.getEndpoint().contains("/CLINIC/{clinicId}/employee/{employeeId}"));
//    }
//    @Test
//    void test_Update() {
//        Mockito.when(endpointRepository.save(endpoint1)).thenReturn(endpoint1);
//        Endpoint result = endpointService.update(endpoint1);
//        assertTrue(result.getEndpoint().contains("/CLINIC/{clinicId}/employee/{employeeId}"));
//    }
////    @Test
////    void test_Delete() {
////EndpointId endpointId = EndpointId.builder()
////        .endpoint(endpoint2.getEndpoint())
////        .method(endpoint2.getMethod())
////        .build();
////        Mockito.when(endpointRepository.deleteById(endpointId));
//////        Endpoint result = endpointService.update(endpoint1);
//////        assertTrue(result.getEndpoint().contains("/CLINIC/{clinicId}/employee/{employeeId}"));
////    }
//
//}