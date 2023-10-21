package datum.app.admin.implement;

import datum.app.admin.model.Service;
import datum.app.admin.repository.ServiceRepository;
import datum.app.admin.service.ServiceService;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;

    @Override
    public List<Service> get() {
        return serviceRepository.findAll();
    }

    @Override
    public Service get(long id) {
        var service = serviceRepository.findById(id);
        if (service.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return service.get();

    }

    @Override
    public List<Service> create(List<Service> services) {
        return serviceRepository.saveAll(services);
    }

    @Override
    public List<Service> update(List<Service> services) {
        return serviceRepository.saveAll(services);
    }

    @Override
    public void delete(long l) {
        serviceRepository.deleteById(l);
    }
}
