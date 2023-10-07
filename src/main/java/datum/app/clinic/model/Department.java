package datum.app.clinic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import datum.config.audit.Auditable;
import jakarta.persistence.*;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
public class Department  extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;
    private Boolean deleted;

//    @JsonBackReference
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Clinic clinic;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="departmentId")
//    @JsonIgnore
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "departmentId")
    private List<Room> rooms = new ArrayList<>();
//    @JsonIgnore
//    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    private OffsetDateTime date;
//    @JsonIgnore
//    @UpdateTimestamp
//    private Instant lastUpdatedOn;
//
//    @JsonIgnore
//    @CreationTimestamp
//    private Instant createdOn;

}
