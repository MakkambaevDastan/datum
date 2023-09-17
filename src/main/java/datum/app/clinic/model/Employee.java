package datum.app.clinic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import datum.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private EmployeeRole role;
    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;
    private Boolean deleted;
    private Boolean everyWeek;
    private Boolean onEvenWeeks;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private User user;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
//    private List<Schedule> schedules;

//    @JsonIgnore
//    @ElementCollection(fetch = FetchType.LAZY)
//    @CollectionTable(name = "FTT_REGISTRI_ESCLUSIONI",
//          foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
//                                  name = "FTT_FK_ESCLUSIONE_TO_REGISTRO"),
//          joinColumns = @JoinColumn(name = "REGISTRO_ID"))
//    @MapKeyColumn(name = "CLAUSOLA_ESCLUSIONE", length = 40, nullable = false)
//    @MapKeyClass(FttEsclusioneType.class)
//    @MapKeyEnumerated(EnumType.STRING)
//    @Column(name = "RECORD_COUNT", nullable = false)
//    protected final Map<FttEsclusioneType, Long> esclusioneRecordCounters = new HashMap<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "dayStart")
    @MapKeyColumn(name = "day", length = 9, nullable = false)
    @MapKeyClass(Day.class)
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "time", nullable = false, columnDefinition = "TIME WITHOUT TIME ZONE")
    private Map<Day, LocalTime> start = new EnumMap<>(Day.class);
//    private EnumMap<Day, LocalTime> start = new EnumMap<>(Map.of(
//            Day.MONDAY, LocalTime.parse("00:00"),
//            Day.TUESDAY, LocalTime.parse("00:00"),
//            Day.WEDNESDAY, LocalTime.parse("00:00"),
//            Day.THURSDAY, LocalTime.parse("00:00"),
//            Day.FRIDAY, LocalTime.parse("00:00"),
//            Day.SATURDAY, LocalTime.parse("00:00"),
//            Day.SUNDAY, LocalTime.parse("00:00")
//    ));

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "dayEnd")
    @MapKeyColumn(name = "day", length = 9, nullable = false)
    @MapKeyClass(Day.class)
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "time", nullable = false, columnDefinition = "TIME WITHOUT TIME ZONE")
    private Map<Day, LocalTime> end = new EnumMap<>(Day.class);
//    private EnumMap<Day, LocalTime> end =  new EnumMap<>(Map.of(
//            Day.MONDAY, LocalTime.parse("00:00"),
//            Day.TUESDAY, LocalTime.parse("00:00"),
//            Day.WEDNESDAY, LocalTime.parse("00:00"),
//            Day.THURSDAY, LocalTime.parse("00:00"),
//            Day.FRIDAY, LocalTime.parse("00:00"),
//            Day.SATURDAY, LocalTime.parse("00:00"),
//            Day.SUNDAY, LocalTime.parse("00:00")
//    ));

    @JsonIgnore
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime date;
}
