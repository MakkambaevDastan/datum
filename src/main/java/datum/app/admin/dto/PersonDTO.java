package datum.app.admin.dto;

import datum.app.admin.model.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Jacksonized
//@Schema(description = "Информация о человеке")
@Schema
public class PersonDTO implements Serializable {

    @Schema
    private List<Phone> phone = new ArrayList<>();
    @Schema(example = "Үсөн")
    @NotBlank(message = "Имя не может быть пустым")
    private String firstname;
    @Schema(example = "Асанов")
    private String surname;
    @Schema(example = "Жаңыбаевич")
    private String patronymic;
    @Schema(example = "пр. Чуй 205, Бишкек, Кыргызстан")
    private String address;
    @Schema(example = "user@gmail.com")
    private String email;
    @Schema(example = "KGZ")
    private String country;
    @Schema(example = "true")
    private Boolean family;
    @Schema(example = "true")
    private Boolean male;
    @Schema(example = "2019-12-31")
    private LocalDate issue;
    @Schema(example = "2029-12-31")
    private LocalDate expiry;
    @Schema(example = "MKK")
    private String authority;
    @Schema(example = "1991-11-21")
    private LocalDate birthDay;
    @Schema(example = "AN")
    private String passportSeries;
    @Schema(example = "1234567")
    private Integer passportNumber;
    @Schema(example = "20101199100001")
    private BigInteger pin;
}
