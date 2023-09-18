package datum.app.clinic.service;

import datum.app.clinic.dto.*;
import datum.app.clinic.dto.ClinicMapper;
import datum.app.clinic.dto.DepartmentMapper;
import datum.app.clinic.dto.RoomMapper;
import datum.app.clinic.model.*;
import datum.app.clinic.model.repositoy.*;
import datum.auth.AuthenticationService;
import datum.user.User;
import datum.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {
    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final RoomRepository roomRepository;
    private final ChairRepository chairRepository;
    private final AuthenticationService authenticationService;
    private final EndpointRepository endpointRepository;
    private final PostRepository postRepository;
    private final PrivilegeRepository privilegeRepository;

    @Override
    public List<Clinic> clinic(ClinicDTO clinicDTO) {
        User user = userRepository.findByEmailIgnoreCase(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow();
        user.getClinics().add(ClinicMapper.INSTANCE.convert(clinicDTO));
        userRepository.save(user);


        return user.getClinics();
    }

    @Override
    public List<Clinic> clinic() {
        User user = userRepository.findByEmailIgnoreCase(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow();
        return user.getClinics();
    }

    @Override
    public List<Privilege> privilege(Long id) {
        if (!clinicRepository.existsById(id))
            return null;
        List<Privilege> privileges = privilegeRepository.getPrivilegeFromClinicId(id);
        List<Endpoint> endpoints = endpointRepository.findAll();
        List<Post> posts = postRepository.findAll();
        if (privileges.size() == posts.size() * endpoints.size())
            return privileges;
        Set<Privilege> newPrivileges = new HashSet<>();
        if (privileges.size() > 0) {
            for (Endpoint endpoint : endpoints) {
                for (Post post : posts) {
                    for (Privilege pri : privileges) {
                        if (!pri.getEndpoint().equals(endpoint.getEndpoint()) &&
                            !pri.getMethod().equals(endpoint.getMethod()) &&
                            !pri.getPostId().equals(post.getId())) {
                            var privil = Privilege.builder()
                                    .clinicId(id)
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
                newPrivileges.forEach(p -> privilegeRepository.save(p));
                privileges.addAll(newPrivileges);
            }
        } else {
            for (Endpoint e : endpoints) {
                for (Post p : posts) {
                    privileges.add(
                            Privilege.builder()
                                    .clinicId(id)
                                    .postId(p.getId())
                                    .endpoint(e.getEndpoint())
                                    .method(e.getMethod())
                                    .bool(true)
                                    .build()
                    );
                }
            }
            privileges.forEach(p->privilegeRepository.save(p));
        }

        return privileges;
    }

    @Override
    public List<Privilege> privilege(List<Privilege> privileges) {
        User user = userRepository.findByEmailIgnoreCase(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow();
        Set<Long> clinics = new HashSet<>();
        List<Privilege> privileges1 = new ArrayList<>();
        user.getClinics().forEach(c -> clinics.add(c.getId()));
        for (Privilege pri : privileges)
            if (clinics.contains(pri.getClinicId()))
                privileges1.add(privilegeRepository.save(pri));
        return privileges1;
    }


    @Override
    public List<Department> department(Long id, List<DepartmentDTO> departmentDTOs) {
        Clinic clinic = clinicRepository.findById(id).orElseThrow();
        departmentDTOs.forEach(
                dto -> clinic.getDepartments().add(DepartmentMapper.INSTANCE.convert(dto))
        );
        clinicRepository.save(clinic);
        return clinic.getDepartments();
    }

    @Override
    public List<Room> room(Long id, List<RoomDTO> roomDTOs) {
        Department department = departmentRepository.findById(id).orElseThrow();
        roomDTOs.forEach(
                dto -> department.getRooms().add(RoomMapper.INSTANCE.convert(dto))
        );
        departmentRepository.save(department);
        return department.getRooms();
    }

    @Override
    public List<Chair> chair(Long id, List<ChairDTO> chairDTOs) {
        Room room = roomRepository.findById(id).orElseThrow();
        chairDTOs.forEach(
                dto -> room.getChairs().add(ChairMapper.INSTANCE.convert(dto))
        );
        roomRepository.save(room);
        return room.getChairs();
    }



//    private  Map<String, Map<String, String>> getEndpoint() {
//        var handler = requestMappingHandlerMapping.getHandlerMethods();
//        Map<String, Map<String, String>> map = new TreeMap<>();
//
//        for (var entry : handler.entrySet()) {
//            try {
//                String pattern = entry.getKey().getPatternValues()
//                        .iterator()
//                        .next();
//                if (pattern.contains("/api/v1/auth/")) continue;
//                if (pattern.contains("swagger")) continue;
//                if (pattern.contains("test")) continue;
//                if (pattern.contains("api-docs")) continue;
//                if (pattern.contains("/password/change")) continue;
//                if (pattern.contains("/error")) continue;
//                String method = entry.getKey()
//                        .getMethodsCondition()
//                        .getMethods()
//                        .iterator()
//                        .next()
//                        .asHttpMethod()
//                        .name();
//                map.put(pattern + " " + method, new TreeMap<>() {{
//                    put(method, pattern);
//                }});
//            } catch (Exception e) {
//            }
//        }
//
//        return map;
//    }

}
