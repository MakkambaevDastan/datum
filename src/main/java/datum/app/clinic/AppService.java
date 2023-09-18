package datum.app.clinic;

import datum.app.clinic.model.Day;
import datum.app.clinic.model.Endpoint;
import datum.app.clinic.model.Post;
import datum.app.clinic.model.repositoy.EndpointRepository;
import datum.app.clinic.model.repositoy.PostRepository;
import datum.user.Role;
import datum.user.User;
import datum.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class AppService {
    private final EndpointRepository endpointRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public Object post() {
        List<Post> posts = postRepository.findAll();
        return Map.of("post", posts);
    }

    public Object endpoint() {
        List<Endpoint> endpoints = endpointRepository.findAll();
        return Map.of("endpoint", endpoints);
    }

    public Object day() {
        return Map.of("day", Day.values());
    }

    //    @Override
    public List<Post> post(List<Post> posts) {
        if (Role.ROOT.equals(
                Role.valueOf(SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority()))
        ) {
            for (Post p : posts) {
                if (p.getId() == null)
                    return null;
            }
            return postRepository.saveAll(posts);
        }
        return null;
//        User user = userRepository.findByEmailIgnoreCase(
//                SecurityContextHolder.getContext().getAuthentication().getName()
//        ).orElseThrow();
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getDetails());
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority());
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority().getClass());
    }
    @PostConstruct
    public void runSetEndpoint() {
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
            } catch (Exception e) {
            }
        }
//        <Endpoint>
        endpointRepository.saveAll(endpoints);
        List<Post> posts = new ArrayList<>();
        if (!postRepository.existsByCode("HEAD"))
            posts.add(Post.builder()
                    .code("HEAD")
                    .en("Chief physician")
                    .ru("Главный врач")
                    .kg("Башкы дарыгер")
                    .build());
        if (!postRepository.existsByCode("ADMIN"))
            posts.add(Post.builder()
                    .code("ADMIN")
                    .en("Administrator")
                    .ru("Администратор")
                    .kg("Башкаруучу")
                    .build());
        if (!postRepository.existsByCode("DENTIST"))
            posts.add(Post.builder()
                    .code("DENTIST")
                    .en("Dentist")
                    .ru("Врач стоматолог")
                    .kg("Тиш доктур")
                    .build());
        if (!postRepository.existsByCode("DENTAL_ASSISTANT"))
            posts.add(Post.builder()
                    .code("DENTAL_ASSISTANT")
                    .en("Dental assistant")
                    .ru("Ассистент стоматолога")
                    .kg("Тиш доктур жардамчысы")
                    .build());
        if (!postRepository.existsByCode("NURSE"))
            posts.add(Post.builder()
                    .code("NURSE")
                    .en("Nurse")
                    .ru("Медицинская сестра")
                    .kg("Медайым")
                    .build());
        postRepository.saveAll(posts);
//        postRepository.saveIfNotExists(Post.builder()
//                .code("NURSE")
//                .en("Nurse")
//                .ru("Медицинская сестра")
//                .kg("Медайым")
//                .build());


//        clinics.add(
//                Clinic.builder()
//                        .name("Clinic")
//                        .departments(
//                                List.of(Department.builder()
//                                        .name("name")
//                                                .address("address")
//                                                .phone("phone")
//                                                .rooms(null)
//                                                .employees(null)
//                                        .build())
//                        )
//                        .build()
//        );
//        clinics.add(
//                Clinic.builder()
//                        .name("Clinic1")
//                        .build()
//        );
//        clinicRepository.saveAll(clinics);

//        List<Clinic> clinics1 = clinicRepository.findAll();
//        List<Endpoint> endpoints1 = endpointRepository.findAll();
//        List<Post> posts1 = postRepository.findAll();
//        List<Privilege> privileges = new ArrayList<>();
////        for (Clinic c : clinics1) {
//            for (Endpoint e : endpoints1) {
//                for (Post p : posts1) {
//                    privileges.add(
//                            Privilege.builder()
//                                    .clinic(12L)
//                                    .post(p.getId())
////                                    .endpointMapped(e)
//                                    .endpoint(e.getEndpoint())
//                                    .method(e.getMethod())
//                                    .bool(false)
//                                    .build()
//                    );
//                }
//            }
//        }
//        clinics1.forEach(
//                c -> {
//                    endpoints1.forEach(
//                            e -> {
//                                posts1.forEach(
//                                        p -> {
//                                            Privilege privilege = Privilege.builder().bool(false).build();
//                                            privilege.setClinic(c);
//                                            privilege.setEndpoint(e.getEndpoint());
//                                            privilege.setMethod(e.getMethod());
//                                            privilege.setPost(p);
//                                            privileges.add(privilege);
//                                        }
//                                );
//                            }
//                    );
//                }
//        );
//        privilegeRepository.saveAll(privileges);

        System.out.println("Сохранение конечных точек");
    }
}
