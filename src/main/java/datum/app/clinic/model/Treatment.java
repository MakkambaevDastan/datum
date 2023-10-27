package datum.app.clinic.model;

import datum.config.audit.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
@SQLDelete(sql = "update treatment set deleted=true where id=?")
@Where(clause = "deleted = false or deleted is null")
public class Treatment extends Auditable<Long> implements Serializable {
    private String description;
    @JdbcTypeCode(SqlTypes.JSON)
    private Tooth tooth;
    @ManyToOne
    private Preiskurant preiskurant;
    private MonetaryAmount discount;
}
