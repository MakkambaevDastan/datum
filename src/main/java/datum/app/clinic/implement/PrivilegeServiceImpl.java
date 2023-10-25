package datum.app.clinic.implement;

import datum.Main;
import datum.app.admin.model.Endpoint;
import datum.app.admin.model.Post;
import datum.app.admin.repository.EndpointRepository;
import datum.app.admin.repository.PostRepository;
import datum.app.clinic.model.Privilege;
import datum.app.clinic.model.PrivilegeID;
import datum.app.clinic.repositoy.ClinicRepository;
import datum.app.clinic.repositoy.PrivilegeRepository;
import datum.app.clinic.service.PrivilegeService;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {
    private final ClinicRepository clinicRepository;
    private final PrivilegeRepository privilegeRepository;
    private final EndpointRepository endpointRepository;
    private final PostRepository postRepository;

    @Override
    public Privilege getPrivilege(
            HttpServletRequest request,
            long clinicId,
            long employeeId
    ) {
        return privilegeRepository.findById(PrivilegeID.builder()
                .clinicId(clinicId)
                .postId(employeeId)
                .method(request.getMethod())
                .endpoint(
                        request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString()
                )
                .build()
        ).orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
    }

    @Override
    public List<Privilege> get(HttpServletRequest request, long clinicId) {
        clinicRepository.countByIdAndUserId(clinicId, Main.getUserId())
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
        List<Privilege> privileges = privilegeRepository.getPrivilegeFromClinicId(clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
        List<Endpoint> endpoints = endpointRepository.findAll();
        List<Post> posts = postRepository.findAll();
        if (privileges.size() == posts.size() * endpoints.size()) return privileges;
        Set<Privilege> newPrivileges = new HashSet<>();
        if (privileges.size() > 0) {
            for (Endpoint endpoint : endpoints) {
                for (Post post : posts) {
                    for (Privilege pri : privileges) {
                        if (!pri.getEndpoint().equals(endpoint.getEndpoint()) &&
                            !pri.getMethod().equals(endpoint.getMethod()) &&
                            !pri.getPostId().equals(post.getId())) {
                            var privil = Privilege.builder()
                                    .clinicId(clinicId)
                                    .postId(post.getId())
                                    .endpoint(endpoint.getEndpoint())
                                    .method(endpoint.getMethod())
                                    .bool(true)
                                    .build();
                            if (!newPrivileges.contains(privil)) {
                                newPrivileges.add(privil);
                            }
                        }
                    }
                }
            }
            if (newPrivileges.size() > 0) {
                privilegeRepository.saveAll(newPrivileges);
                privileges.addAll(newPrivileges);
            }
        } else {
            for (Endpoint e : endpoints) {
                for (Post p : posts) {
                    privileges.add(
                            Privilege.builder()
                                    .clinicId(clinicId)
                                    .postId(p.getId())
                                    .endpoint(e.getEndpoint())
                                    .method(e.getMethod())
                                    .bool(true)
                                    .build()
                    );
                }
            }
            privilegeRepository.saveAll(privileges);
        }
        return privileges;
    }

    @Override
    public List<Privilege> update(
            HttpServletRequest request,
            long clinicId,
            List<Privilege> privileges
    ) {
        if (clinicRepository.countByIdAndUserId(clinicId, Main.getUserId()).isEmpty())
            throw new ExceptionApp(404, Message.NOT_FOUND);
        try {
            privilegeRepository.saveAll(privileges);
        } catch (Exception e) {
            throw new ExceptionApp(400, Message.INVALID_ID);
        }
        return privileges;
    }

}
