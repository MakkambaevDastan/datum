package datum.app.admin.repository;

import datum.app.admin.model.Endpoint;
import datum.app.admin.model.EndpointId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EndpointRepository extends JpaRepository<Endpoint, EndpointId> {
    Optional<Endpoint> findEndpointByMethodAndEndpoint(String method, String endpoint);
}
