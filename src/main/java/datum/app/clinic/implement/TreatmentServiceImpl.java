package datum.app.clinic.implement;

import datum.app.clinic.dto.TreatmentUpdate;
import datum.app.clinic.mapping.TreatmentMapper;
import datum.app.clinic.model.Anamnesis;
import datum.app.clinic.model.Treatment;
import datum.app.clinic.repositoy.AnamnesisRepository;
import datum.app.clinic.repositoy.TreatmentRepository;
import datum.app.clinic.service.TreatmentService;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentServiceImpl implements TreatmentService {
    private final TreatmentRepository treatmentRepository;
    private final AnamnesisRepository anamnesisRepository;

    @Override
    public List<Treatment> getAll(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId
    ) {
        return treatmentRepository.findAllByClinicIdAndAnamnesisId(clinicId, anamnesisId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
    }

    @Override
    public Treatment get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId,
            long treatmentId
    ) {
        return treatmentRepository.findByIdAndClinicId(treatmentId, clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
    }

    @Override
    public Treatment add(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId,
            Treatment treatment
    ) {
        Anamnesis anamnesis = anamnesisRepository.findByIdAndClinicId(anamnesisId, clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
        anamnesis.getTreatments().add(treatment);
        anamnesisRepository.save(anamnesis);
        return treatment;
    }

    @Override
    public List<Treatment> addAll(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId,
            List<Treatment> treatments
    ) {
        Anamnesis anamnesis = anamnesisRepository.findByIdAndClinicId(anamnesisId, clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
        anamnesis.getTreatments().addAll(treatments);
        anamnesisRepository.save(anamnesis);
        return treatments;
    }

    @Override
    public Treatment update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId,
            long treatmentId,
            TreatmentUpdate treatmentUpdate
    ) {
        Treatment treatment = treatmentRepository.findByIdAndClinicId(treatmentId, clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
        TreatmentMapper.INSTANCE.update(treatmentUpdate, treatment);
        return treatmentRepository.save(treatment);
    }

    @Override
    public void delete(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId,
            long treatmentId
    ) {
        if (treatmentRepository.existsByIdAndClinicId(treatmentId, clinicId)) {
            treatmentRepository.deleteById(treatmentId);
        }
    }
}
