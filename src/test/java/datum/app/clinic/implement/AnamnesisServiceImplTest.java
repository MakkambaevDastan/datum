//package datum.app.clinic.implement;
//
//import datum.app.clinic.repositoy.AnamnesisRepository;
//import datum.app.clinic.repositoy.EmployeeRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//class AnamnesisServiceImplTest {
//    @Mock
//    private AnamnesisRepository anamnesisRepository;
//    @Mock
//    private EmployeeRepository employeeRepository;
//    @InjectMocks
//    private AnamnesisServiceImpl anamnesisService;
//    HttpServletRequest request = null;
//    Long clinicId = 1L;
//    Long employeeId = 1L;
//    Long departmentId = 1L;
//    Long id = 1L;
//
//    @Test
//    void get() {
////        Mockito.when(employeeRepository.existsByIdAndClinicId(id, clinicId))
////                .thenReturn(true);
////        Mockito.when(anamnesisRepository.findAllByEmployeeId(id)).thenReturn(List.of());
////        var anamnesis = anamnesisService.get(
////                request,
////                clinicId,
////                employeeId,
////                departmentId,
////                id
////        );
////        assertNotNull(anamnesis);
//    }
//}