package datum.app.clinic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
@SQLDelete(sql = "update department set deleted=true where id=?")
@Where(clause = "deleted = false")
public class Department  extends Auditable<Long> implements Serializable {

    private String name;
    private String address;
    private String phone;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="departmentId")
    private List<Employee> employees = new ArrayList<>();
}
