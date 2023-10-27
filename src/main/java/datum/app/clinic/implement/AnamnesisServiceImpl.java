package datum.app.clinic.implement;

import datum.app.clinic.dto.AnamnesisDTO;
import datum.app.clinic.mapping.AnamnesisMapper;
import datum.app.clinic.model.Anamnesis;
import datum.app.clinic.model.Employee;
import datum.app.clinic.repositoy.AnamnesisRepository;
import datum.app.clinic.repositoy.EmployeeRepository;
import datum.app.clinic.service.AnamnesisService;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnamnesisServiceImpl implements AnamnesisService {
    private final AnamnesisRepository anamnesisRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Anamnesis> get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id
    ) {
        return anamnesisRepository.findAllByClinicIdAndEmployeeId(clinicId,id)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
    }

    @Override
    public Anamnesis get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId
    ) {
        return anamnesisRepository.findByIdAndClinicId(anamnesisId, clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
    }

    @Override
    public Anamnesis create(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            Anamnesis anamnesis
    ) {
        Employee employee = employeeRepository.findByIdAndClinicId(id, clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
        anamnesis.setEmployee(employee);
        return anamnesisRepository.save(anamnesis);
    }

//    @Override
//    public Anamnesis update(
//            HttpServletRequest request,
//            long clinicId,
//            long employeeId,
//            long departmentId,
//            long id,
//            long anamnesisId,
//            AnamnesisDTO anamnesisDTO
//    ) {
//        Anamnesis anamnesis = anamnesisRepository.findByIdAndClinicIdAndEmployeeId(anamnesisId, clinicId, employeeId)
//                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
//        AnamnesisMapper.INSTANCE.update(anamnesisDTO, anamnesis);
//        return anamnesisRepository.save(anamnesis);
//    }

    @Override
    public void delete(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId
    ) {
        if (!anamnesisRepository.existsByIdAndClinicId(anamnesisId, clinicId))
            throw new ExceptionApp(404, Message.NOT_FOUND);
        anamnesisRepository.deleteById(anamnesisId);
    }
}
