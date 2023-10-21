package datum.app.admin.controller;

import datum.app.admin.model.Endpoint;
import datum.app.admin.model.EndpointId;
import datum.app.admin.service.EndpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ADMIN/ENDPOINT")
@RequiredArgsConstructor
public class EndpointController {
    private final EndpointService endpointService;

    @GetMapping
    public ResponseEntity<List<Endpoint>> getEndpoint() {
        return ResponseEntity.ok(endpointService.get());
    }
    @PutMapping
    public ResponseEntity<Endpoint> updateEndpoint(
            @RequestBody Endpoint endpoint
    ) {
        return ResponseEntity.ok(endpointService.update(endpoint));
    }

    @DeleteMapping
    public void deleteEndpoint(
            @RequestBody EndpointId endpointId
    ) {
        endpointService.delete(endpointId);
    }
}
