package datum.app.clinic.model;

import datum.app.admin.model.Phone;
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
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
@SQLDelete(sql = "update department set deleted=true where id=?")
@Where(clause = "deleted = false or deleted is null")
public class Department  extends Auditable<Long> implements Serializable {

    private String name;
    @JdbcTypeCode(SqlTypes.JSON)
    private Address address;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="departmentId")
    private List<Employee> employees = new ArrayList<>();
}
