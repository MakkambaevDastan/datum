package datum.app.clinic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import datum.config.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
public class Chair  extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore
//    @JsonProperty
    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;
    private Boolean deleted;

//    @JsonIgnore
////    @JsonProperty
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
