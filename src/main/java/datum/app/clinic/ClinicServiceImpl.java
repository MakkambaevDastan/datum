package datum.app.clinic;

import datum.app.admin.model.Endpoint;
import datum.app.admin.model.Post;
import datum.app.admin.model.repository.EndpointRepository;
import datum.app.admin.model.repository.PostRepository;
import datum.app.clinic.model.dto.*;
import datum.app.clinic.model.*;
import datum.app.clinic.model.repositoy.*;
import datum.authenticate.AuthenticationService;
import datum.authenticate.user.User;
import datum.authenticate.user.UserRepository;
import datum.config.exception.ExceptionApp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

import  datum.Main;

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
    private final EmployeeService employeeService;

    @Override
    public Object clinic(ClinicDTO clinicDTO) {
        User user = userRepository.findByEmailIgnoreCase(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow();
        Clinic clinic = ClinicMapper.INSTANCE.convert(clinicDTO);
        EmployeeDTO employeeDTO = EmployeeDTO.builder().post("OWNER").build();
        Employee employee = EmployeeMapper.INSTANCE.convert(employeeDTO, postRepository);
        employee.setUser(user);
        employeeRepository.save(employee);

        clinic.getDepartments().get(0).setEmployees(List.of(employee));
//        user.getEmployee().add(employee);
        user.getClinics().add(clinic);
        userRepository.save(user);
        return clinic;
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
        var userId = Main.getUserId();
        if(userId.isPresent()) {
            var clinic = clinicRepository.selectWhereIdAndUserId(id, userId.get(), "OWNER");
            if(clinic.isPresent()) {
                clinic.get().getDepartments().addAll(DepartmentMapper.INSTANCE.list(departmentDTOs));
                clinicRepository.save(clinic.get());
                return clinic.get().getDepartments();
            }
            throw new ExceptionApp(404,"Клиника не найден");
        }
        throw new ExceptionApp(404,"Ваша учетная запись не найдена");
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
}
