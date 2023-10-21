package datum.app.admin.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EndpointId implements Serializable {
    private String method;
    private String endpoint;
}
