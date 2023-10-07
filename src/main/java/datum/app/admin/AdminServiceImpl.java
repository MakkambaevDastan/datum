package datum.app.admin;

import datum.Main;
import datum.app.admin.model.ICD10;
import datum.app.admin.model.Post;
import datum.app.admin.model.dto.ICD10DTO;
import datum.app.admin.model.dto.ICD10Mapper;
import datum.app.admin.model.dto.ServiceDTO;
import datum.app.admin.model.dto.ServiceMapper;
import datum.app.admin.model.repository.ICD10Repository;
import datum.app.admin.model.repository.PostRepository;
import datum.app.admin.model.repository.ServiceRepository;
import datum.authenticate.user.Role;
import datum.config.exception.ExceptionApp;
import datum.lib.CyrillicLatinConverter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final PostRepository postRepository;
    private final ICD10Repository icd10Repository;
    private final ServiceRepository serviceRepository;
    @Override
    public List<Post> post(List<Post> posts) {
//        if (Role.ADMIN.equals(
//                Role.valueOf(SecurityContextHolder
//                        .getContext()
//                        .getAuthentication()
//                        .getAuthorities()
//                        .iterator()
//                        .next()
//                        .getAuthority()))
//        ) {
            for (Post p : posts) {
                if (p.getId() == null)
                    return null;
            }
            return postRepository.saveAll(posts);
//        }
//        return null;
    }
    @Override
    public Object service(
            HttpServletRequest request,
            HttpServletResponse response,
            List<ServiceDTO> serviceDTO
    ) {
//        if (Role.ADMIN.equals(
//                Role.valueOf(SecurityContextHolder
//                        .getContext()
//                        .getAuthentication()
//                        .getAuthorities()
//                        .iterator()
//                        .next()
//                        .getAuthority()))
//        ) {
            List<datum.app.admin.model.Service> service = ServiceMapper.INSTANCE.convert(serviceDTO);
            serviceRepository.saveAll(service);
            return service;
//        }
    }

    @Override
    public Object icd10(
            HttpServletRequest request,
            HttpServletResponse response,
            List<ICD10DTO> icd10DTOs
    ) {
        List<ICD10> icd10s = icd10DTOs.stream().map(ICD10Mapper.INSTANCE::convert).toList();
        icd10s.forEach(icd10 -> {
            if(Main.isInteger(icd10.getName().substring(5, 6))) {
                var code = icd10.getName().substring(0, 6).toUpperCase().trim();
                if (!(code.charAt(0) >= 'A' && code.charAt(0) <= 'Z')) {
                    Function<String, String> cyrillicToLatin = CyrillicLatinConverter::cyrilicToLatin;
                    code = cyrillicToLatin.apply(code);
                }
                var name = icd10.getName().substring(6).trim();
                if (code.substring(3, 4).equals(".")) {
                    icd10.setCode(code);
                    var block = code.substring(0, 1).toUpperCase();
                    var category = Integer.parseInt(code.substring(1, 3));
                    var subcategory = Integer.parseInt(code.substring(4, 5));
                    var item = Integer.parseInt(code.substring(5, 6));

                    var icd = icd10Repository.findFirstByBlock(block);
                    icd10.setChapter(icd.getChapter());
                    icd10.setBlock(block);
                    icd10.setCategory(category);
                    icd10.setSubcategory(subcategory);
                    icd10.setItem(item);
                    icd10.setName(name);
                }
            }
            else throw new ExceptionApp(400, "Не правильный код MKБ-10: "+icd10.getName());
        });
        icd10Repository.saveAll(icd10s);
        return icd10s;
    }

    @Override
    public Object icd10() {
        return null;
    }
}
