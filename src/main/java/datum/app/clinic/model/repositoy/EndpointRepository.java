package datum.app.clinic.model.repositoy;

import datum.app.clinic.model.Endpoint;
import datum.app.clinic.model.EndpointId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndpointRepository extends JpaRepository<Endpoint, EndpointId> {
}
