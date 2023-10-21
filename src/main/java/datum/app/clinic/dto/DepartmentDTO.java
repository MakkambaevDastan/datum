package datum.app.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class DepartmentDTO {
    private String name;
    private String address;
    private String phone;
}
