package datum.app;

import datum.app.admin.model.Day;
import datum.app.admin.model.Endpoint;
import datum.app.admin.model.Post;
import datum.app.admin.model.repository.EndpointRepository;
import datum.app.admin.model.repository.PostRepository;
import datum.app.admin.model.repository.ICD10Repository;
import datum.app.admin.model.repository.ServiceRepository;
import datum.authenticate.user.*;
import datum.config.exception.ExceptionApp;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DatumServiceImpl implements DatumService {
    private final EndpointRepository endpointRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final ICD10Repository icd10Repository;
    private final PersonRepository personRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public Object post() {
        List<Post> posts = postRepository.findAll();
        return Map.of("post", posts);
    }

    @Override
    public Object endpoint() {
        List<Endpoint> endpoints = endpointRepository.findAll();
        return Map.of("endpoint", endpoints);
    }

    @Override
    public Object day() {
        return Map.of("day", Day.values());
    }

    @Override
    public Object person(PersonDTO personDTO) {
        var person = PersonMapper.INSTANCE.convert(personDTO);
        personRepository.save(person);
        return person;
    }

    @Override
    public Object person(Integer page, Integer size, String field) {
       return personRepository.findAll(PageRequest.of(page, size, Sort.by(field)));
    }
    public Object person(Long id, Integer page, Integer size) {
        return personRepository.findAll(id, page, size);
    }
    private Map<String, Object> convertToResponse(final Page<Person> pagePersons) {
        Map<String, Object> response = new HashMap<>();
        response.put("persons", pagePersons.getContent());
        response.put("current-page", pagePersons.getNumber());
        response.put("total-items", pagePersons.getTotalElements());
        response.put("total-pages", pagePersons.getTotalPages());
        return response;
    }
    @Override
    public Object icd10() {
       return  icd10Repository.findAllByBlockAndCategoryBetween("K", 0, 14);
    }

    @Override
    public Object service() {
        return serviceRepository.findAll();
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
        if (!postRepository.existsByCode("OWNER"))
            posts.add(Post.builder()
                    .code("OWNER")
                    .en("Owner")
                    .ru("Владелец")
                    .kg("Ээси")
                    .build());
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
        System.out.println("Сохранение конечных точек");
    }


}
