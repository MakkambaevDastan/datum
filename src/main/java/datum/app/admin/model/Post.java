package datum.app.admin.model;

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
public class Post  extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String en;
    private String ru;
    private String kg;
    @Column(columnDefinition = "TEXT")
    private String description;

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
