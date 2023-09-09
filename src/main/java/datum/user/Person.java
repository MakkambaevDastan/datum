package datum.user;

import com.nimbusds.openid.connect.sdk.assurance.claims.CountryCode;
import com.nimbusds.openid.connect.sdk.assurance.claims.ISO3166_1Alpha2CountryCode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String surname;
    private String patronymic;
    private String address;
    private String phone;
    private String email;
    private String country;
    private Boolean male;
    private Boolean family;
    @Temporal(TemporalType.DATE)
    private LocalDate issue;
    @Temporal(TemporalType.DATE)
    private LocalDate expiry;
    private String authority;
    @Temporal(TemporalType.DATE)
    private LocalDate birthDay;
    private String passportSeries;
    private Integer passportNumber;
    private Long PIN;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime date;
}
