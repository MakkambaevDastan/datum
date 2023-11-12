package datum.app;


import datum.config.exception.Message;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/TEST")
@RequiredArgsConstructor
public class Demo {

    @GetMapping
    public ResponseEntity<Object> get(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(Map.of(1, "Hello, Get!"));
    }
}

