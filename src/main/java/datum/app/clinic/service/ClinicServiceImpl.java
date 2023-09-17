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
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    @Override
    public Object clinic(List<ClinicDTO> clinicDTOs) {
        User user = userRepository.findByEmailIgnoreCase(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow();
        clinicDTOs.forEach(
                dto -> user.getClinics().add(ClinicMapper.INSTANCE.convert(dto))

        );
        userRepository.save(user);
        return user.getClinics();
    }

    @Override
    public Object department(Long id, List<DepartmentDTO> departmentDTOs) {
        Clinic clinic = clinicRepository.findById(id).orElseThrow();
        departmentDTOs.forEach(
                dto->clinic.getDepartments().add(DepartmentMapper.INSTANCE.convert(dto))
        );
        clinicRepository.save(clinic);
        return clinic.getDepartments();
    }

    @Override
    public Object room(Long id, List<RoomDTO> roomDTOs) {
        Department department = departmentRepository.findById(id).orElseThrow();
        roomDTOs.forEach(
                dto->department.getRooms().add(RoomMapper.INSTANCE.convert(dto))
        );
        departmentRepository.save(department);
        return department.getRooms();
    }

    @Override
    public List<Chair> chair(Long id, List<ChairDTO> chairDTOs) {
        Room room = roomRepository.findById(id).orElseThrow();
        chairDTOs.forEach(
                dto-> room.getChairs().add(ChairMapper.INSTANCE.convert(dto))
        );
        roomRepository.save(room);
        return room.getChairs();
    }


}
