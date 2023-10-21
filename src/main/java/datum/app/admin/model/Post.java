package datum.app.admin.model;

import datum.config.audit.Auditable;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
@SQLDelete(sql = "update post set deleted=true where id=?")
@Where(clause = "deleted = false")
public class Post  extends Auditable<Long> implements Serializable {
//    @Id
//    @Tsid
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
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
