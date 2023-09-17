package datum.app.clinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;
    private Boolean deleted;
//    @ManyToOne
//    @JoinColumn
//    private Department department;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "roomId")
    private List<Chair> chairs;

    @JsonIgnore
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime date;
}
