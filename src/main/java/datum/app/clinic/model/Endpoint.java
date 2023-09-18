package datum.app.clinic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Delegate;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Immutable;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.time.OffsetDateTime;


@Data
@Builder
//@MappedSuperclass
//@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
@IdClass(EndpointId.class)
public class Endpoint {
    //    @Embeddable
//    @EqualsAndHashCode
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public class EndpointId implements Serializable {
//        @Column(name = "method")
//        private String method;
//        @Column(name = "endpoint")
//        private String endpoint;
//
////        public EndpointId() {
////
////        }
//    }
//    @Delegate
//    @EmbeddedId
//    private EndpointId id;
//    @Id
//    @Enumerated(EnumType.STRING)
//    @Column(name = "method", insertable = false, updatable = false)
//    private RequestMethod method;
    @Id
    @Column(name = "method", insertable = false, updatable = false)
    private String method;
    @Id
    @Column(name = "endpoint", insertable = false, updatable = false)
    private String endpoint;
    private String name;
    @JsonIgnore
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime date;
}
