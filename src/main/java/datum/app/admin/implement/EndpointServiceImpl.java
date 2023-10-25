package datum.app.admin.implement;

import datum.app.admin.model.Endpoint;
import datum.app.admin.model.EndpointId;
import datum.app.admin.repository.EndpointRepository;
import datum.app.admin.service.EndpointService;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class EndpointServiceImpl implements EndpointService {
    private final EndpointRepository endpointRepository;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public List<Endpoint> get() {
        return endpointRepository.findAll();
    }


    @Override
    public Endpoint create(Endpoint endpoint) {
            return  endpointRepository.save(endpoint);
    }

    @Override
    public Endpoint update(Endpoint endpoint) {
        return endpointRepository.save(endpoint);
    }

    @Override
    public void delete(EndpointId endpointId) {
        endpointRepository.deleteById(endpointId);
    }

    @PostConstruct
    public void run() {
        var handler = requestMappingHandlerMapping.getHandlerMethods();
        Map<String, Map<String, String>> map = new TreeMap<>();
        List<Endpoint> endpoints = new ArrayList<>();
        for (var entry : handler.entrySet()) {
            try {
                String pattern = entry.getKey().getPatternValues()
                        .iterator()
                        .next();
//                if (pattern.contains("/authenticate")) continue;
//                if (pattern.contains("swagger")) continue;
//                if (pattern.contains("test")) continue;
//                if (pattern.contains("api-docs")) continue;
//                if (pattern.contains("/error")) continue;
                if (pattern.contains("CLINIC")) {
                    String method = entry.getKey()
                            .getMethodsCondition()
                            .getMethods()
                            .iterator()
                            .next()
                            .asHttpMethod()
                            .name();
                    endpoints.add(Endpoint.builder()
                            .endpoint(pattern)
                            .method(method)
                            .name(pattern)
                            .build());
                }
            } catch (Exception e) {
            }
        }
        endpointRepository.saveAll(endpoints);
    }
}
