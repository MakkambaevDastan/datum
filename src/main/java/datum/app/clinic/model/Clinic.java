package datum.app.clinic.model;

import datum.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;
    private Boolean deleted;
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "clinicId")
    private List<Department> departments;
    @JsonIgnore
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime date;
}
