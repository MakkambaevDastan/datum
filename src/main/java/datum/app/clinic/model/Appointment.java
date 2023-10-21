package datum.app.clinic.model;

import datum.app.admin.model.Person;
import datum.config.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
@SQLDelete(sql = "update appointment set deleted=true where id=?")
@Where(clause = "deleted = false")
public class Appointment  extends Auditable<Long> implements Serializable {
    private Boolean visible;
    private String description;
    private Instant start;
    private Integer minute;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Person person;
}
