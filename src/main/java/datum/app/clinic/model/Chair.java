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
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
public class Chair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore
//    @JsonProperty
    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;
    private Boolean deleted;

    @JsonIgnore
//    @JsonProperty
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime date;
}
