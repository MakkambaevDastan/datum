package datum.app.clinic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;
    private Boolean deleted;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="departmentId")
//    @JsonIgnore
    private List<Employee> employees;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "departmentId")
    private List<Room> rooms;
    @JsonIgnore
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime date;

}
