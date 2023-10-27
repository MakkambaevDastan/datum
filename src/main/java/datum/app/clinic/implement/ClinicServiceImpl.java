package datum.app.clinic.implement;

import datum.app.admin.repository.PostRepository;
import datum.app.clinic.dto.*;
import datum.app.clinic.mapping.*;
import datum.app.clinic.model.*;
import datum.app.clinic.repositoy.*;
import datum.app.clinic.service.ClinicService;
import datum.authenticate.User;
import datum.authenticate.UserRepository;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
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
        return clinicRepository.findAllByCreatedBy(Main.getUserId())
                .orElseThrow(() -> new ExceptionApp(404, "У вас нет клиник"));
    }

    @Override
    public Clinic get(Long clinicId) {
        return clinicRepository.findByIdAndCreatedBy(clinicId, Main.getUserId())
                .orElseThrow(() -> new ExceptionApp(404, "Клиника не найден"));
    }

    @Override
    public Clinic create(Clinic clinic) {
        User user = userRepository.findById(Main.getUserId())
                .orElseThrow(() -> new ExceptionApp(404, Message.USER_NOT_FOUND));
            EmployeeDTO employeeDTO = EmployeeDTO.builder().post("OWNER").build();
            Employee employee = EmployeeMapper.INSTANCE.convert(employeeDTO, postRepository);
            employee.setUser(user);
            employeeRepository.save(employee);
            clinic.getDepartments().get(0).setEmployees(List.of(employee));
            user.getClinics().add(clinic);
            userRepository.save(user);
            return clinicRepository.findById(clinic.getId())
                    .orElseThrow(
                            () -> new ExceptionApp(500, Message.INTERNAL_SERVER_ERROR)
                    );

    }

    @Override
    public String update(long clinicId, String name) {
        clinicRepository.updateClinic(clinicId, Main.getUserId(), name);
        return clinicRepository.findNameById(clinicId)
                .orElseThrow(
                        () -> new ExceptionApp(500, Message.INTERNAL_SERVER_ERROR)
                );
    }

    @Override
    public void delete(long clinicId) {
        Long userId = Main.getUserId();
        clinicRepository.deleteByIdAndCreatedBy(clinicId, userId);
    }

}
