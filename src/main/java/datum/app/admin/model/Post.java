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
@SQLDelete(sql = "update post set deleted=true where id=?")
@Where(clause = "deleted = false or deleted is null")
public class Post  extends Auditable<Long> implements Serializable {
    private String code;
    private String en;
    private String ru;
    private String kg;
    @Column(columnDefinition = "TEXT")
    private String description;
}
