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
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn
    private Post post;
    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;
    private Boolean deleted;
    private Boolean everyWeek;
    private Boolean onEvenWeeks;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "dayStart")
    @MapKeyColumn(name = "day", length = 9, nullable = false)
    @MapKeyClass(Day.class)
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "time", nullable = false, columnDefinition = "TIME WITHOUT TIME ZONE")
    private Map<Day, LocalTime> start = new EnumMap<>(Day.class);

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "dayEnd")
    @MapKeyColumn(name = "day", length = 9, nullable = false)
    @MapKeyClass(Day.class)
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "time", nullable = false, columnDefinition = "TIME WITHOUT TIME ZONE")
    private Map<Day, LocalTime> end = new EnumMap<>(Day.class);

    @JsonIgnore
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime date;
}
