package datum.app.therapy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import datum.authenticate.user.Person;
import datum.config.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
public class Appointment  extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean visible;
    @JsonIgnore
    private Boolean deleted;
    private String description;
    private Instant start;
    private Integer minute;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "appointment_person",
//            joinColumns = {@JoinColumn(name = "appointment_id")},
//            inverseJoinColumns = {@JoinColumn(name = "person_id")}
//    )

    private Person person;
//    private List<Person> person;
}
