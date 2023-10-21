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
@SQLDelete(sql = "update service set deleted=true where id=?")
@Where(clause = "deleted = false")
public class Service extends Auditable<Long> implements Serializable {
//    @Id
//    @Tsid
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @Column(length = 7)
    private String code;
    @Column(length = 1)
    private String view;
    @Column(length = 2)
    private Integer section;
    @Column(length = 3)
    private Integer number;
    private String name;
    private String kg;

//    @JsonBackReference
//    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Preiskurant> preiskurants = new ArrayList<>();
//    public void addPreiskurant(Preiskurant preiskurant) {
//        preiskurants.add(preiskurant);
//        preiskurant.setService(this);
//    }
//
//    public void removePreiskurant(Preiskurant preiskurant) {
//        preiskurants.remove(preiskurant);
//        preiskurant.setService(null);
//    }
//    @JsonIgnore
//    @UpdateTimestamp
//    private Instant lastUpdatedOn;
//
//    @JsonIgnore
//    @CreationTimestamp
//    private Instant createdOn;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Service )) return false;
//        return id != null && id.equals(((Service) o).getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
}
