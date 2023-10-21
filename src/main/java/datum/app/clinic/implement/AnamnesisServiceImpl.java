package datum.app.clinic.implement;

import datum.app.clinic.model.Anamnesis;
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
        var anamnesis = anamnesisRepository.findAllByEmployeeId(id);
        if (anamnesis.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return anamnesis.get();
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
        var anamnesis = anamnesisRepository.findByIdAndClinicId(anamnesisId, clinicId);
        if (anamnesis.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return anamnesis.get();
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
        var employee = employeeRepository.findByIdAndClinicId(id,clinicId);
        if (employee.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        employee.get().addAnamnesis(anamnesis);
        employeeRepository.save(employee.get());
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
        var anamnesis1 = anamnesisRepository.findByIdAndClinicId(anamnesisId, clinicId);
        if (anamnesis1.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);

        anamnesis1.get().setClinicalDiagnosis(anamnesis.getClinicalDiagnosis());
        anamnesis1.get().setTooth(anamnesis.getTooth());
        anamnesis1.get().setAnamnesisDetails(anamnesis.getAnamnesisDetails());
        anamnesisRepository.save(anamnesis1.get());

        return anamnesis1.get();
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
