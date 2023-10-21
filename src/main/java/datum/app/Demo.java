//package datum.app;
//
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.HandlerMapping;
//import org.springframework.web.util.UrlPathHelper;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
////@RestController
////@RequestMapping("/demo")
////@RequiredArgsConstructor
//public class Demo {
////    private final PersonRepository personRepository;
//
//    @GetMapping
//    public ResponseEntity<Object> get(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) {
////        Map<String, Object> map = new HashMap<>();
////        map.put("getMethod", request.getMethod());
////        map.put("getPathInfo", request.getPathInfo());
////        map.put("getServletPath", request.getServletPath());
////        map.put("getContextPath", request.getContextPath());
////        map.put("getRequestURI", request.getRequestURI());
////        map.put("getRequestURL", request.getRequestURL());
////        map.put("getPathTranslated", request.getPathTranslated());
////
////        return ResponseEntity.ok(map);
//        return ResponseEntity.ok("Hello, Get!");
//    }
//
//    @PostMapping
//    public ResponseEntity<Object> post(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) {
////        List<Person> persons = List.of(
////                Person.builder().firstname("person1").build(),
////                Person.builder().firstname("person2").build()
////        );
////        personRepository.saveAll(persons);
////        persons.get(0).setDeleted(true);
////        personRepository.save(persons.get(0));
////        var persons2 = personRepository.findAll();
////        return ResponseEntity.ok(List.of(persons, persons2));
//        return ResponseEntity.ok("Hello, Post!");
//    }
//
//    @PutMapping
//    public ResponseEntity<Object> put(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) {
//        return ResponseEntity.ok("Hello, Put!");
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Object> delete(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) {
//        return ResponseEntity.ok("Hello, Delete!");
//    }
//
//    @PatchMapping("/{var}/{id}")
//    public ResponseEntity<Object> patch(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            @PathVariable("var") String var,
//            @PathVariable("id") String id
//    ) {
//        return ResponseEntity.ok("Hello, " + var + " " + id);
//////        UrlPathHelper
////        Map<String, Object> map = new HashMap<>();
////        map.put("var", var);
////        map.put("id", id);
////        map.put("getMethod", request.getMethod());
////        map.put("getPathInfo", request.getPathInfo());
////        map.put("getServletPath", request.getServletPath());
////        map.put("getContextPath", request.getContextPath());
////        map.put("getRequestURI", request.getRequestURI());
////        map.put("getRequestURL", request.getRequestURL());
////        map.put("getPathTranslated", request.getPathTranslated());
////        map.put("getRemoteUser", request.getRemoteUser());
////        map.put("getHttpServletMapping", request.getHttpServletMapping());
////        map.put("getQueryString", request.getQueryString());
////        map.put("getScheme", request.getScheme());
////        map.put("getTrailerFields", request.getTrailerFields());
////        map.put("getParameterMap", request.getParameterMap());
////        map.put("getProtocol", request.getProtocol());
////        map.put("getAttributeNames", request.getAttributeNames());
////        map.put("getAttribute",  request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
//
//
////        return ResponseEntity.ok(map);
//    }
//
//    public ResponseEntity<Object> test(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) {
//        return ResponseEntity.ok("Hello, Patch!");
//    }
//}
//
