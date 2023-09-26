package datum;

import datum.app.clinic.model.Endpoint;
//import datum.app.clinic.model.EndpointId;
import datum.app.clinic.model.repositoy.EmployeeRepository;
import datum.app.clinic.model.repositoy.EndpointRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class Test {
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    @GetMapping
    public ResponseEntity<Object> get() {
        return null;
    }
    @PostMapping
    public ResponseEntity<Object> post(HttpServletRequest request, HttpServletResponse response) {
        response.addCookie(new Cookie("test1", "test1"));
        response.addCookie(new Cookie("test2", "test2"));
        return ResponseEntity.ok(request.getCookies());
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

    public ResponseEntity<Object> test(
            @PathVariable("id") String idS) {
        Long id = Long.parseLong(idS);
        var handler = requestMappingHandlerMapping.getHandlerMethods();
        Map<String, Map<String, String>> map = new TreeMap<>();
        List<Endpoint> endpoints = new ArrayList<>();
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
                map.put(pattern + " " + method, new TreeMap<>() {{
                    put(method, pattern);
                }});
            } catch (Exception e) {
            }
        }
        return ResponseEntity.ok(map);
    }
}

