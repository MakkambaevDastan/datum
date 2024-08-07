package datum.app.clinic.model;

import datum.config.audit.Auditable;
import jakarta.persistence.*;
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
@SQLDelete(sql = "update preiskurant set deleted=true where id=?")
@Where(clause = "deleted = false or deleted is null")
public class Preiskurant  extends Auditable<Long> implements Serializable {
    @Column(columnDefinition = "TEXT")
    private String description;
    private Long service;
    @JdbcTypeCode(SqlTypes.JSON)
    private MonetaryAmount price;
}
