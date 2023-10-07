package datum.app.clinic.model;

import datum.app.admin.model.Post;
import datum.app.therapy.model.Anamnesis;
import datum.app.therapy.model.Appointment;
import datum.authenticate.user.User;
import datum.config.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
public class Employee  extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Post post;

    @JdbcTypeCode(SqlTypes.JSON)
    private Schedule schedule;
    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;
    private Boolean deleted;
    private Boolean locked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private List<Anamnesis> anamnesis = new ArrayList<>();
//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "dayStart")
//    @MapKeyColumn(name = "day", length = 9, nullable = false)
//    @MapKeyClass(Day.class)
//    @MapKeyEnumerated(EnumType.STRING)
//    @Column(name = "time", nullable = false, columnDefinition = "TIME WITHOUT TIME ZONE")
//    private Map<Day, DayTime> start = new EnumMap<>(Day.class);
//    @JsonBackReference
//    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Appointment> appointments = new ArrayList<>();
//    public void addAppointment(Appointment appointment) {
//        appointments.add(appointment);
//        appointment.setEmployee(this);
//    }
//
//    public void removeAppointment(Appointment appointment) {
//        appointments.remove(appointment);
//        appointment.setEmployee(null);
//    }


//    @JsonIgnore
//    @UpdateTimestamp
//    private Instant lastUpdatedOn;
//
//    @JsonIgnore
//    @CreationTimestamp
//    private Instant createdOn;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "dayStart")
//    @MapKeyColumn(name = "day", length = 9, nullable = false)
//    @MapKeyClass(Day.class)
//    @MapKeyEnumerated(EnumType.STRING)
//    @Column(name = "time", nullable = false, columnDefinition = "TIME WITHOUT TIME ZONE")
//    private Map<Day, LocalTime> start = new EnumMap<>(Day.class);
//
//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "dayEnd")
//    @MapKeyColumn(name = "day", length = 9, nullable = false)
//    @MapKeyClass(Day.class)
//    @MapKeyEnumerated(EnumType.STRING)
//    @Column(name = "time", nullable = false, columnDefinition = "TIME WITHOUT TIME ZONE")
//    private Map<Day, LocalTime> end = new EnumMap<>(Day.class);

//    @JsonIgnore
//    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    private OffsetDateTime date;

}
