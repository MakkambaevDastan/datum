package datum.app.clinic.model;

import datum.app.admin.model.ICD10;
import datum.app.admin.model.Person;
import datum.config.audit.Auditable;
import io.swagger.v3.oas.annotations.media.Schema;
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
@SQLDelete(sql = "update anamnesis set deleted=true where id=?")
@Where(clause = "deleted = false or deleted is null")
@Schema
public class Anamnesis extends Auditable<Long> implements Serializable {
    @OneToOne
    private Employee employee;
    @ManyToOne
    private Person person;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "anamnesis_icd10",
            joinColumns = {@JoinColumn(name = "anamnesis_id")},
            inverseJoinColumns = {@JoinColumn(name = "icd10_id")}
    )
    private List<ICD10> clinicalDiagnosis = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Tooth> tooth = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    private List<AnamnesisDetails> anamnesisDetails = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "anamnesis_id")
    private List<Treatment> treatments = new ArrayList<>();
}
