package datum.app.clinic.service;

import datum.app.clinic.controller.ClinicDTO;
import datum.app.clinic.controller.DepartmentDTO;
import datum.app.clinic.controller.EmployeeDTO;
import datum.app.clinic.controller.RoomDTO;
import datum.app.clinic.model.*;
import datum.app.clinic.repositoy.*;
import datum.auth.AuthenticationResponse;
import datum.auth.AuthenticationService;
import datum.config.JwtService;
import datum.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClinicServiceImplements implements ClinicService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final ClinicRepository clinicRepository;
    private final DepartmentRepository departmentRepository;
    private final RoomRepository roomRepository;
    private final ChairRepository chairRepository;
    private final AuthenticationService authenticationService;

    @Override
    public Boolean employee(HttpServletRequest request, EmployeeDTO employeeDTO) {
        var employee = Employee.builder()
                .role(employeeDTO.getRole())
                .enabled(true)
                .build();
        if (employeeDTO.getYourself()) {
            var user = userRepository.findByEmailIgnoreCase(
                    SecurityContextHolder.getContext().getAuthentication().getName()
            ).orElseThrow();
            employee.setUser(user);
            employeeRepository.save(employee);
            return true;
        } else if (Objects.isNull(employeeDTO.getUser())) {
            return false;
        } else {
            authenticationService.register(request, employeeDTO.getUser());
            var user = userRepository.findByEmailIgnoreCase(
                    employeeDTO.getUser().getEmail()
            ).orElseThrow();
            employee.setEnabled(false);
            employee.setUser(user);
            employeeRepository.save(employee);
            return true;
        }
    }

    @Override
    public Boolean addClinic(ClinicDTO clinicDTO) {
        var user = userRepository.findByEmailIgnoreCase(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow();

        var employee = Employee.builder()
                .enabled(true)
                .role(EmployeeRole.HEAD)
                .build();

        var clinic = Clinic.builder()
                .name(clinicDTO.getHead().getName())
                .enabled(true)
                .build();

        var department = Department.builder()
                .name(clinicDTO.getHead().getName())
                .address(clinicDTO.getHead().getAddress())
                .phone(clinicDTO.getHead().getPhone())
                .enabled(true)
                .build();

        employee.setUser(user);
        employeeRepository.save(employee);

        clinic.setEmployee(employee);
        clinicRepository.save(clinic);

        departmentRepository.save(department);

        employee.setDepartment(department);
        employeeRepository.save(employee);

        clinic.setDepartment(department);
        clinicRepository.save(clinic);

        department.setClinic(clinic);
        departmentRepository.save(department);

        addRooms(clinicDTO.getHead().getRooms(), department);
        addDepartments(clinicDTO.getDepartments(), clinic);

        return true;
    }

    private void addRooms(List<RoomDTO> rooms, Department department) {
        rooms.forEach(
                (r) -> {
                    var room = Room.builder()
                            .name(r.getName())
                            .build();
                    room.setDepartment(department);
                    roomRepository.save(room);

                    r.getChairs().forEach((c) -> {
                        var chair = Chair.builder()
                                .name(c.getName())
                                .enabled(true)
                                .build();
                        chair.setRoom(room);
                        chairRepository.save(chair);
                    });

                }
        );
    }

    private void addDepartments(List<DepartmentDTO> departments, Clinic clinic) {
        departments.forEach(
                (dep) -> {
                    var department = Department.builder()
                            .name(dep.getName())
                            .address(dep.getAddress())
                            .phone(dep.getPhone())
                            .enabled(true)
                            .build();
                    department.setClinic(clinic);

                    departmentRepository.save(department);
                    addRooms(dep.getRooms(), department);
                }
        );
    }
//    private void addEmployee(EmployeeDTO employeeDTO, RegisterRequest registerRequest){
//
//    }
//    private void addUser(UserDTO userDTO)
}
