package datum.app.clinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean enabled=true;

    @OneToOne
    @JoinColumn
    private Department department;

    @OneToMany(mappedBy = "clinic")
    private List<Department> departments;

    @ManyToOne
    @JoinColumn
    private Employee employee;
}
