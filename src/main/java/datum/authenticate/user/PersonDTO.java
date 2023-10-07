package datum.authenticate.user;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
//import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Jacksonized
public class PersonDTO {
    private String firstname;
    private String surname;
    private String patronymic;
    private String address;
    private String phone;
    private String email;
    private String country;
    private Boolean family;
    private Boolean male;
    private LocalDate issue;
    private LocalDate expiry;
    private String authority;
    private LocalDate birthDay;
    private String passportSeries;
    private Integer passportNumber;
    private Long PIN;
}
