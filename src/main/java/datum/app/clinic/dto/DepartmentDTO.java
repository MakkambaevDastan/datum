package datum.app.clinic.dto;

import datum.app.admin.model.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@Schema
public class DepartmentDTO implements Serializable {
    @Schema(example = "Главный", description = "Название")
    private String name;
    @Schema(example = "пр. Чүй 205, Бишкек, Кыргызстан")
    private String address;
    @Schema
    private List<Phone> phone = new ArrayList<>();
}
