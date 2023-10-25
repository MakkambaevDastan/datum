package datum.app.clinic.implement;

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
        if(!employeeRepository.existsByIdAndClinicId(id, clinicId))
            throw new ExceptionApp(404, Message.NOT_FOUND);
        return anamnesisRepository.findAllByEmployeeId(id)
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
        Employee employee = employeeRepository.findByIdAndClinicId(id,clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
        employee.addAnamnesis(anamnesis);
        employeeRepository.save(employee);
        return anamnesis;
    }

    @Override
    public Anamnesis update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId,
            Anamnesis anamnesis
    ) {
        var anamnesis1 = anamnesisRepository.findByIdAndClinicId(anamnesisId, clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
        anamnesis1.setClinicalDiagnosis(anamnesis.getClinicalDiagnosis());
        anamnesis1.setTooth(anamnesis.getTooth());
        anamnesis1.setAnamnesisDetails(anamnesis.getAnamnesisDetails());
        anamnesisRepository.save(anamnesis1);

        return anamnesis1;
    }

    @Override
    public void delete(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId
    ) {
        if(!anamnesisRepository.existsByIdAndClinicId(anamnesisId, clinicId))
            throw new ExceptionApp(404, Message.NOT_FOUND);
        anamnesisRepository.deleteById(anamnesisId);
    }
}
