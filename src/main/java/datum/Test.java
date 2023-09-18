package datum;

import datum.app.clinic.model.Endpoint;
//import datum.app.clinic.model.EndpointId;
import datum.app.clinic.model.Privilege;
import datum.app.clinic.model.repositoy.EmployeeRepository;
import datum.app.clinic.model.repositoy.EndpointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

//@RestController
//@RequestMapping("test")
@RequiredArgsConstructor
public class Test {
    private final EmployeeRepository employeeRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final EndpointRepository endpointRepository;

    @GetMapping
    public ResponseEntity<Object> get() {
        return ResponseEntity.ok("Hello, Get!");
    }

    @PostMapping
    public ResponseEntity<Object> post() {
        return ResponseEntity.ok("Hello, Post!");
    }

    @PutMapping
    public ResponseEntity<Object> put() {
        return ResponseEntity.ok("Hello, Put!");
    }

    @DeleteMapping
    public ResponseEntity<Object> delete() {
        return ResponseEntity.ok("Hello, Delete!");
    }

    @PatchMapping
    public ResponseEntity<Object> patch() {
        return ResponseEntity.ok("Hello, Patch!");
    }

//    @PostMapping("/{id}")
    public ResponseEntity<Object> test(
            @PathVariable("id") String idS) {
        Long id = Long.parseLong(idS);
        var handler = requestMappingHandlerMapping.getHandlerMethods();
        Map<String, Map<String, String>> map = new TreeMap<>();
        List<Endpoint> endpoints = new ArrayList<>();
//        Endpoint endpoint = new Endpoint();
//        Endpoint.EnpointId endpointId = new Endpoint.EnpointId();
        for (var entry : handler.entrySet()) {
            try {
                String pattern = entry.getKey().getPatternValues()
                        .iterator()
                        .next();
                if (pattern.contains("/api/v1/auth/")) continue;
                if (pattern.contains("swagger")) continue;
                if (pattern.contains("test")) continue;
                if (pattern.contains("api-docs")) continue;
                if (pattern.contains("/password/change")) continue;
                if (pattern.contains("/error")) continue;
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
//                endpoint.setEndpoint(pattern);
//                endpoint.setName(pattern);
//                endpoint.setMethod(method);
//                endpoints.add(endpoint);
//                endpoints.add(Endpoint.builder()
//                                .id(
//                                        Endpoint.EndpointId.having()
//                                                .endpoint(pattern)
//                                                .method(method)
//                                                .build()
//                                )
//                                .name(pattern)
//                                .endpoint(pattern)
//                                .method(RequestMethod.valueOf(method))
//                        .build()
//                );
//                System.out.println(pattern+" "+method);
                map.put(pattern + " " + method, new TreeMap<>() {{
                    put(method, pattern);
                }});
            } catch (Exception e) {
            }
        }
//        endpointRepository.saveAll(endpoints);
//        endpoints.forEach(System.out::println);
//        map.forEach((k, v) -> System.out.println(k + " " + v));
        return ResponseEntity.ok(map);
    }

//    public ResponseEntity<Object> test(
//            @PathVariable("id") String idS,
//            @RequestBody Privilege privilege) {
//        Long id = Long.parseLong(idS);
//        var handler = requestMappingHandlerMapping.getHandlerMethods();
//        Map<String, Map<String, String>> map = new TreeMap<>();
//        for (var entry : handler.entrySet()) {
//            try {
//                String pattern = entry.getKey().getPatternValues()
//                        .iterator()
//                        .next();
//                if (pattern.contains("/api/v1/auth/")) continue;
//                if (pattern.contains("swagger")) continue;
//                if (pattern.contains("test")) continue;
//                if (pattern.contains("api-docs")) continue;
//                if (pattern.contains("/password/change")) continue;
//                if (pattern.contains("/error")) continue;
//                String method = entry.getKey()
//                        .getMethodsCondition()
//                        .getMethods()
//                        .iterator()
//                        .next()
//                        .asHttpMethod()
//                        .name();
//                map.put(pattern + " " + method, new TreeMap<>() {{
//                    put(method, pattern);
//                }});
//            } catch (Exception e) {
//            }
//        }
//        map.forEach((k, v) -> System.out.println(k + " " + v));
//        return ResponseEntity.ok(map);
//    }
}

//class CustomSpringEvent extends ApplicationEvent {
//    private String message;
//
//    public CustomSpringEvent(Object source, String message) {
//        super(source);
//        this.message = message;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//}

