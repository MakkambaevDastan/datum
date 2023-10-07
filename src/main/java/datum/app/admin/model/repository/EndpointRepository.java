package datum.app.admin.model.repository;

import datum.app.admin.model.Endpoint;
import datum.app.admin.model.EndpointId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndpointRepository extends JpaRepository<Endpoint, EndpointId> {
}
