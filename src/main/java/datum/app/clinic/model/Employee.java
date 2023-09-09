package datum.app.clinic.model;

import datum.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.sql.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private Date begin;
    @Enumerated(EnumType.STRING)
    private EmployeeRole role;
    private Boolean enabled=true;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime date;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "employee")
    private List<Clinic> clinic;

    @ManyToOne
    @JoinColumn
    private Department department;

    @ManyToMany
    private List <Chair> chairs;

//    @OneToMany(mappedBy = "employee")
//    private List<Schedule> schedules;

}
