package datum.app.admin.model;

import datum.config.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
@SQLDelete(sql = "update icd10 set deleted=true where id=?")
@Where(clause = "deleted = false or deleted is null AND dent=true")
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

    public boolean isDent() {
        return dent;
    }
}
