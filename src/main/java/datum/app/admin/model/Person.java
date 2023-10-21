package datum.app.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import datum.app.clinic.model.Anamnesis;
import datum.config.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Jacksonized
@SQLDelete(sql = "update person set deleted=true where id=?")
@Where(clause = "deleted = false")
public class Person extends Auditable<Long> implements Serializable {
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
    private Long pin;

//    @JsonIgnore
//    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Anamnesis> anamnesis=new ArrayList<>();
}
