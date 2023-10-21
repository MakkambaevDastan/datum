package datum.app.clinic.model;

import datum.app.admin.model.ICD10;
import datum.app.admin.model.Person;
import datum.config.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
@SQLDelete(sql = "update clinic set deleted=true where id=?")
@Where(clause = "deleted = false")
public class Clinic extends Auditable<Long> implements Serializable {
    private String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "clinicId")
    private List<Department> departments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "clinicId")
    private List<Preiskurant> preiskurants = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "clinic_person",
            joinColumns = {@JoinColumn(name = "clinic_id")},
            inverseJoinColumns = {@JoinColumn(name = "person_id")}
    )
    private List<Person> persons = new ArrayList<>();
    public void addPerson(Person person) {
        persons.add(person);
    }
}
