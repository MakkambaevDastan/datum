package datum.app.admin;

import datum.app.admin.model.Post;
import datum.app.admin.model.dto.ICD10DTO;
import datum.app.admin.model.dto.ServiceDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface AdminService {
    List<Post> post(List<Post> posts);
    Object service(HttpServletRequest request, HttpServletResponse response, List<ServiceDTO> serviceDTOs);

    Object icd10(HttpServletRequest request, HttpServletResponse response, List<ICD10DTO> icd10DTOs);
    Object icd10();

}
