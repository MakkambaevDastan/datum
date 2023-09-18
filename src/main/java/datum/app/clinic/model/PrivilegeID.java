package datum.app.clinic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;


@Data
@Builder
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PrivilegeID implements Serializable {
    @Column(name = "clinicId")
    private Long clinicId;
    @Column(name = "postId")
    private Long postId;
    @Column(name = "method")
    private String method;
    @Column(name = "endpoint")
    private String endpoint;
}
