package datum.app.clinic.model;

import datum.config.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
@IdClass(PrivilegeID.class)
public class Privilege  extends Auditable<String> implements Serializable {
    @Id
    @Column(name = "clinicId")
    private Long clinicId;
    @Id
    @Column(name = "postId")
    private Long postId;
    @Id
    @Column(name = "endpoint")
    private String endpoint;
    @Id
    @Column(name = "method")
    private String method;
    private Boolean bool;
}
