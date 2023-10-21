package datum.app.admin.service;

import datum.app.admin.model.Endpoint;
import datum.app.admin.model.EndpointId;

import java.util.List;

public interface EndpointService {
    List<Endpoint> get();

    List<Endpoint> create(List<Endpoint> endpoints);

    Endpoint update(Endpoint endpoint);

    void delete(EndpointId endpointId);
}
