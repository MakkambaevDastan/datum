package datum.app.admin.service;

import datum.app.admin.model.Endpoint;
import datum.app.admin.model.EndpointId;

import java.util.List;

public interface EndpointService {
    List<Endpoint> get();

    Endpoint create(Endpoint endpoint);

    Endpoint update(Endpoint endpoint);

    void delete(EndpointId endpointId);
}
