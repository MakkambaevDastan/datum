package datum.testdata;

import datum.app.admin.model.Phone;
import datum.app.clinic.dto.ClinicDTO;
import datum.app.clinic.dto.DepartmentDTO;
import datum.app.clinic.model.Address;

import java.util.List;

public class ClinicData {
    public static long CLINIC_ID = 0L;
    public static long DEPARTMENT_ID = 0L;

    public static ClinicDTO createClinicDTO() {
        return ClinicDTO.builder()
                .name("Clinic" + ++CLINIC_ID)
                .departments(
                        List.of(
                                DepartmentDTO.builder()
                                        .name("Department" + ++DEPARTMENT_ID)
                                        .address(Address.builder()
                                                .country("Kyrgyzstan")
                                                .phone(List.of(
                                                        Phone.builder()
                                                                .number("+996777123456")
                                                                .whatsapp(true)
                                                                .build()
                                                ))
                                                .build())

                                        .build()
                        )
                )
                .build();
    }
}
