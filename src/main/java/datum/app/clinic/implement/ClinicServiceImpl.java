package datum.app.clinic.implement;

import datum.app.admin.repository.PostRepository;
import datum.app.clinic.dto.*;
import datum.app.clinic.mapping.*;
import datum.app.clinic.model.*;
import datum.app.clinic.repositoy.*;
import datum.app.clinic.service.ClinicService;
import datum.authenticate.UserRepository;
import datum.config.exception.ExceptionApp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import datum.Main;

@Service
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {
    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PostRepository postRepository;

    @Override
    public List<Clinic> get() {
        Long userId = Main.getUserId();
//        Optional<List<Clinic>> clinics = clinicRepository.findAllByUserId(userId);
        Optional<List<Clinic>> clinics = clinicRepository.findAllByCreatedBy(userId);
        if (clinics.isPresent())
            return clinics.get();
        throw new ExceptionApp(404, "У вас нет клиник");
    }

    @Override
    public Clinic get(Long clinicId) {
        Long userId = Main.getUserId();
//        Optional<Clinic> clinic = clinicRepository.findByIdAndUserId(clinicId, userId);
        Optional<Clinic> clinic = clinicRepository.findByIdAndCreatedBy(clinicId, userId);
        if (clinic.isPresent())
            return clinic.get();
        throw new ExceptionApp(404, "Клиника не найден");
    }

    @Override
    public Clinic create(Clinic clinic) {
        var userId = Main.getUserId();
        var user = userRepository.findById(userId);
        var employeeDTO = EmployeeDTO.builder().post("OWNER").build();
        var employee = EmployeeMapper.INSTANCE.convert(employeeDTO, postRepository);
        employee.setUser(user.get());
        employeeRepository.save(employee);
        clinic.getDepartments().get(0).setEmployees(List.of(employee));
        user.get().getClinics().add(clinic);
        userRepository.save(user.get());
        return clinicRepository.findById(clinic.getId()).get();
    }

    @Override
    public String update(long clinicId, String name) {
        Long userId = Main.getUserId();
        clinicRepository.updateClinic(clinicId, userId, name);
        var newName = clinicRepository.findNameById(clinicId);
        if (newName.isPresent())
            return newName.get();
        throw new ExceptionApp(500, "Внутренняя ошибка сервера");
    }

    @Override
    public void delete(long clinicId) {
        Long userId = Main.getUserId();
        clinicRepository.deleteByIdAndCreatedBy(clinicId, userId);
    }

//    @Override
//    public boolean isClinicEmployee(Long clinicId, Long userId) {
//        return false;
//    }
//
//    @Override
//    public List<Long> getEmployeeId(Long clinicId, Long userId) {
//        return null;
//    }
//
//    @Override
//    public boolean isRightPoint(Long clinicId, Long userId, HttpServletRequest request) {
//        return false;
//    }

//    @Override
//    public Object clinic(ClinicDTO clinicDTO) {
//        User user = userRepository.findByEmailIgnoreCase(
//                SecurityContextHolder.getContext().getAuthentication().getName()
//        ).orElseThrow();
//        Clinic clinic = ClinicMapper.INSTANCE.convert(clinicDTO);
//        EmployeeDTO employeeDTO = EmployeeDTO.builder().post("OWNER").build();
//        Employee employee = EmployeeMapper.INSTANCE.convert(employeeDTO, postRepository);
//        employee.setUser(user);
//        employeeRepository.save(employee);
//
//        clinic.getDepartments().get(0).setEmployees(List.of(employee));
////        user.getEmployee().add(employee);
//        user.getClinics().add(clinic);
//        userRepository.save(user);
//        return clinic;
//    }
//
//    @Override
//    public List<Clinic> clinic() {
//        User user = userRepository.findByEmailIgnoreCase(
//                SecurityContextHolder.getContext().getAuthentication().getName()
//        ).orElseThrow();
//        return user.getClinics();
//    }




//    @Override
//    public List<Department> department(Long id, List<DepartmentDTO> departmentDTOs) {
//        var userId = Main.getUserId();
////        if(userId.isPresent()) {
//        var clinic = clinicRepository.selectWhereIdAndUserId(id, userId, "OWNER");
//        if (clinic.isPresent()) {
//            clinic.get().getDepartments().addAll(DepartmentMapper.INSTANCE.convert(departmentDTOs));
//            clinicRepository.save(clinic.get());
//            return clinic.get().getDepartments();
//        }
//        throw new ExceptionApp(404, "Клиника не найден");
////        }
////        throw new ExceptionApp(404,"Ваша учетная запись не найдена");
//    }

//    @Override
//    public List<Room> room(Long id, List<RoomDTO> roomDTOs) {
//        Department department = departmentRepository.findById(id).orElseThrow();
//        roomDTOs.forEach(
//                dto -> department.getRooms().add(RoomMapper.INSTANCE.convert(dto))
//        );
//        departmentRepository.save(department);
//        return department.getRooms();
//    }
//
//    @Override
//    public List<Chair> chair(Long id, List<ChairDTO> chairDTOs) {
//        Room room = roomRepository.findById(id).orElseThrow();
//        chairDTOs.forEach(
//                dto -> room.getChairs().add(ChairMapper.INSTANCE.convert(dto))
//        );
//        roomRepository.save(room);
//        return room.getChairs();
//    }
//
//
//    private boolean checkPrivele() {
//        return false;
//    }
}
