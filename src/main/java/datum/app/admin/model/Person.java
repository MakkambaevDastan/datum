package datum.app.admin.model;

import datum.config.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
//@Builder
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Jacksonized
@SQLDelete(sql = "update person set deleted=true where id=?")
@Where(clause = "deleted = false or deleted is null")
public class Person extends Auditable<Long> implements Serializable {
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "phone_person",
//            joinColumns = {@JoinColumn(name = "person_id")},
//            inverseJoinColumns = {@JoinColumn(name = "phone_id")}
//    )
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Phone> phone = new ArrayList<>();
    private String firstname;
    private String surname;
    private String patronymic;
    private String address;
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
    private BigInteger pin;
}
