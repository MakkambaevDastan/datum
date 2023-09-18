package datum.app.clinic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
@IdClass(PrivilegeID.class)
public class Privilege {
//    @Embeddable
//    public class PrivilegeID implements Serializable {
//        @Column(name = "clinicId")
//        private Long clinicId;
//        @Column(name = "postId")
//        private Long postId;
//        @Column(name = "method")
//        private String method;
//        @Column(name = "endpoint")
//        private String endpoint;
//    }

//    @JsonIgnore
//    @EmbeddedId
//    private PrivilegeID id;

//    @JsonIgnore
//    @Id
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "clinicId",referencedColumnName="id", insertable = false, updatable = false)
//    private Clinic clinic;
    @Id
    @Column(name = "clinicId")
    private Long clinicId;

    //    @Id
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "postId",referencedColumnName="id", insertable = false, updatable = false)
//    private Post post;
    @Id
    @Column(name = "postId")
    private Long postId;
    //    @Id
//    @ManyToOne
//    @JoinColumns({
//            @JoinColumn(name = "method", referencedColumnName = "method", insertable = false, updatable = false),
//            @JoinColumn(name = "endpoint", referencedColumnName = "endpoint", insertable = false, updatable = false)
//    })
//    private Endpoint endpointMapped;
    @Id
    @Column(name = "endpoint")
    private String endpoint;
    @Id
    @Column(name = "method")
    private String method;
    private Boolean bool;
    @JsonIgnore
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime date;
}
