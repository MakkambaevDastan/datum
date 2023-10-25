package datum.app.admin.controller;

import datum.Main;
import datum.app.admin.dto.ServiceDTO;
import datum.app.admin.mapping.ServiceMapper;
import datum.app.admin.model.Service;
import datum.app.admin.service.ServiceService;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ADMIN/service")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;
    @GetMapping
    public ResponseEntity<List<Service>> getAllService() {
        return ResponseEntity.ok(serviceService.get());
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<Service> getService(
            @PathVariable("serviceId") String serviceId
    ) {
           return ResponseEntity.ok(serviceService.get(Main.parseLong(serviceId)));
    }

    @PostMapping
    public ResponseEntity<List<Service>> createService(
            @RequestBody List<ServiceDTO> serviceDTOs
    ) {
        var services = ServiceMapper.INSTANCE.convert(serviceDTOs);
        return ResponseEntity.ok(serviceService.create(services));
    }

    @PutMapping
    public ResponseEntity<List<Service>> updateService(
            @RequestBody List<Service> services
    ) {
        return ResponseEntity.ok(serviceService.update(services));
    }

    @DeleteMapping("/{serviceId}")
    public void deleteService(
            @PathVariable("serviceId") String serviceId
    ) {
            serviceService.delete(Main.parseLong(serviceId));
    }
}
