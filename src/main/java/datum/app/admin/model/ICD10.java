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
//@EntityListeners(AuditingEntityListener.class)
//@EnableJpaAuditing
//@Audited
@SQLDelete(sql = "update icd10 set deleted=true where id=?")
@Where(clause = "deleted = false")
public class ICD10  extends Auditable<Long> implements Serializable {
    private String code; //K00.11 // K00.11
    private String chapter; // I
    private String block; //K //K00-K14
    private Integer category; //00 // K00
    private Integer subcategory; // 0 // K00.0
    private Integer item; // 0 // K00.00
    private String name;
    private String description;
    private Boolean dent;
}
