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
//@EntityListeners(AuditingEntityListener.class)
//@EnableJpaAuditing
//@Audited
public class ICD10  extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code; //K00.11 // K00.11
    private String chapter; // I
    private String block; //K //K00-K14
    private Integer category; //00 // K00
    private Integer subcategory; // 0 // K00.0
    private Integer item; // 0 // K00.00
    private String name;
    private String description;
    private Boolean dent;




//    @JsonIgnore
//    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    private Instant createdOn;
//    @JsonIgnore
//    @UpdateTimestamp
//    private Instant lastUpdatedOn;
//
//    @JsonIgnore
//    @CreationTimestamp
//    private Instant createdOn;
}
