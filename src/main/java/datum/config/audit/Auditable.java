package datum.config.audit;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
//@Where(clause = "deleted = false")
public abstract class Auditable<U> implements Serializable {
    @Id @Tsid
    private java.lang.Long id;

    @CreatedBy
    protected U createdBy;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected Instant creationDate;

    @LastModifiedBy
    protected U lastModifiedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected Instant lastModifiedDate;

    @Column(columnDefinition = "boolean default false")
    private Boolean deleted = false;

}
