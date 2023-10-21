package datum.app.admin.service;

import datum.app.admin.model.Service;

import java.util.List;

public interface ServiceService {
    List<Service> get();
    Service get(long serviceId);

    List<Service> create(List<Service> services);

    List<Service> update(List<Service> services);

    void delete(long serviceId);
}
