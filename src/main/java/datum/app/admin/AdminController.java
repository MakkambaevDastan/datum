package datum.app.admin;

import datum.app.admin.model.Post;
import datum.app.admin.model.dto.ICD10DTO;
import datum.app.admin.model.dto.ServiceDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    @PutMapping("/post")
    public ResponseEntity<List<Post>> post(@RequestBody List<Post> posts){
        return ResponseEntity.ok(adminService.post(posts));
    }
    @PostMapping("/service")
    public ResponseEntity<Object> service(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody List<ServiceDTO> serviceDTOs
    ){
        return ResponseEntity.ok(adminService.service( request, response, serviceDTOs ));
    }
    @PostMapping("/icd10")
    public ResponseEntity<Object> icd10(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody List<ICD10DTO> icd10DTOs
    ){
        return ResponseEntity.ok(adminService.icd10( request, response, icd10DTOs ));
    }
}
