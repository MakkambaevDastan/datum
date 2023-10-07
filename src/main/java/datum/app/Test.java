package datum.app;

import datum.app.admin.model.Endpoint;
//import datum.app.admin.model.EndpointId;
import datum.app.clinic.model.repositoy.EmployeeRepository;
import datum.app.admin.model.repository.PostRepository;
import datum.app.therapy.TherapyService;
import datum.app.admin.model.dto.ICD10DTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

//@RestController
//@RequestMapping("/test")
@RequiredArgsConstructor
public class Test {

    @GetMapping
    public ResponseEntity<Object> get(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok("Hello, Get!");
    }
    @PostMapping
    public ResponseEntity<Object> post(
            HttpServletRequest request,
            HttpServletResponse response
            ) {

        return ResponseEntity.ok("Hello, Post!");
    }

    @PutMapping
    public ResponseEntity<Object> put(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok("Hello, Put!");
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok("Hello, Delete!");
    }

    @PatchMapping
    public ResponseEntity<Object> patch(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok("Hello, Patch!");
    }

    public ResponseEntity<Object> test(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok("Hello, Patch!");
    }
}

