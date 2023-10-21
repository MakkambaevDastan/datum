package datum.app.admin.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
@IdClass(EndpointId.class)
public class Endpoint implements Serializable {
    @Id
    @Column(name = "method", insertable = false, updatable = false)
    private String method;
    @Id
    @Column(name = "endpoint", insertable = false, updatable = false)
    private String endpoint;
    private String name;
}
