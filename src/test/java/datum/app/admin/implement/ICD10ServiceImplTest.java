//package datum.app.admin.implement;
//
//import datum.app.admin.dto.ICD10DTO;
//import datum.app.admin.mapping.ICD10Mapper;
//import datum.app.admin.model.ICD10;
//import datum.app.admin.repository.ICD10Repository;
//import datum.config.exception.ExceptionApp;
//import datum.config.exception.Message;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class ICD10ServiceImplTest {
//    @Mock
//    private ICD10Repository icd10Repository;
//
//    @InjectMocks
//    private ICD10ServiceImpl icd10Service;
//    ICD10 mockICD10 = ICD10.builder()
//            .id(1L)
//            .code("K00-K14")
//            .chapter("XI")
//            .block("K")
//            .category(null)
//            .subcategory(null)
//            .item(null)
//            .name("Diseases of oral cavity, salivary glands and jaws")
//            .description("description")
//            .dent(true)
//            .build();
//           ICD10DTO icd10DTO1= ICD10DTO.builder()
//                    .name("K00.19 Сверхкомплектные зубы неуточненные") // K latin
//                    .dent(true)
//                    .build();
//    ICD10DTO icd10DTO2=ICD10DTO.builder()
//                    .name("К00.20 Макродентия") // К кириллица
//                    .dent(true)
//                    .build();
//    ICD10DTO icd10DTO3_error=            ICD10DTO.builder()
//            .name("К00.2 Макродентия")
//            .dent(true)
//            .build();
//    ICD10 icd10_dental = ICD10.builder()
//            .code("K00.20")
//            .chapter("XI")
//            .block("K")
//            .category(0)
//            .subcategory(2)
//            .item(0)
//            .name("Макродентия")
//            .description("description")
//            .dent(true)
//            .build();
//    List<ICD10DTO> icd10DTOs = List.of(icd10DTO1, icd10DTO2);
//    List<ICD10DTO> icd10DTOs_err = List.of(icd10DTO3_error);
//    Optional<List<ICD10>> icd10_dentals = Optional.of(List.of(icd10_dental));
//    @Test
//    void test_GetDental() {
//        Mockito.when(icd10Repository.findAllByBlockAndCategoryBetween("K", 0, 14))
//                .thenReturn(icd10_dentals);
//        List<ICD10> result = icd10Service.getDental();
//        assertEquals(1, result.size());
//        assertEquals("XI", result.get(0).getChapter());
//        assertEquals("Макродентия", result.get(0).getName());
//    }
//    @Test
//    void test_GetDentalThrow() {
//        Mockito.when(icd10Repository.findAllByBlockAndCategoryBetween("K", 0, 14))
//                .thenReturn(Optional.empty());
//        ExceptionApp exception = assertThrows(
//                ExceptionApp.class,
//                () -> icd10Service.getDental()
//        );
//        assertEquals(404, exception.getCode());
//        assertEquals(Message.NOT_FOUND, exception.getMessage());
//    }
//    @Test
//    void test_Get() {
//        Mockito.when(icd10Repository.findAll()).thenReturn(List.of(mockICD10));
//        List<ICD10> result = icd10Service.get();
//        assertEquals(1, result.size());
//        assertEquals(("K00-K14"), result.get(0).getCode());
//    }
//    @Test
//    void test_GetById() {
//        Long id = mockICD10.getId();
//        Mockito.when(icd10Repository.findById(id)).thenReturn(Optional.of(mockICD10));
//        ICD10 result = icd10Service.get(id);
//        assertEquals(("K00-K14"), result.getCode());
//    }
//    @Test
//    void test_GetByIdThrow() {
//        Long id = mockICD10.getId();
//        Mockito.when(icd10Repository.findById(id)).thenReturn(Optional.empty());
//        ExceptionApp exception = assertThrows(
//                ExceptionApp.class,
//                () ->  icd10Service.get(id)
//        );
//        assertEquals(404, exception.getCode());
//        assertEquals(Message.NOT_FOUND, exception.getMessage());
//    }
//    @Test
//    void test_Create() {
//
//        Mockito.when(icd10Repository.findFirstByBlock("K")).thenReturn(mockICD10);
//
//
//        List<ICD10> testICD10 = ICD10Mapper.INSTANCE.convert(icd10DTOs);
//
//        List<ICD10> result = icd10Service.create(testICD10);
//
//        assertEquals(2, result.size());
//
//        ICD10 icd10 = result.get(0);
//        assertEquals("K00.19", icd10.getCode());
//        assertEquals("XI", icd10.getChapter());
//        assertEquals("K", icd10.getBlock());
//        assertEquals(0, icd10.getCategory());
//        assertEquals(1, icd10.getSubcategory());
//        assertEquals(9, icd10.getItem());
//        assertEquals("Сверхкомплектные зубы неуточненные", icd10.getName());
//        assertTrue(icd10.isDent());
//        assertNull(icd10.getDescription());
//
//        ICD10 icd101 = result.get(1);
//        assertEquals("K00.20", icd101.getCode());
//        assertEquals("XI", icd101.getChapter());
//        assertEquals("K", icd101.getBlock());
//        assertEquals(0, icd101.getCategory());
//        assertEquals(2, icd101.getSubcategory());
//        assertEquals(0, icd101.getItem());
//        assertEquals("Макродентия", icd101.getName());
//        assertTrue(icd101.isDent());
//        assertNull(icd101.getDescription());
//    }
//
//    @Test
//    void test_CreateThrow() {
//
//        List<ICD10> testICD10 = ICD10Mapper.INSTANCE.convert(icd10DTOs_err);
//
//        ExceptionApp exception = assertThrows(
//                ExceptionApp.class,
//                () -> icd10Service.create(testICD10)
//        );
//        assertEquals(400, exception.getCode());
//        assertEquals("Не правильный код MKБ-10: К00.2", exception.getMessage());
//
//    }
//    @Test
//    void test_Update() {
//        Mockito.when(icd10Repository.saveAll(List.of(mockICD10))).thenReturn(List.of(mockICD10));
//        List<ICD10> result = icd10Service.update(List.of(mockICD10));
//        assertEquals(1, result.size());
//        assertEquals(("K00-K14"), result.get(0).getCode());
//    }
//}