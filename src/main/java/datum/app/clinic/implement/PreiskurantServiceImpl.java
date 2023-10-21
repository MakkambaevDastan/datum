package datum.app.clinic.implement;

import datum.app.clinic.model.MonetaryAmount;
import datum.app.clinic.model.Preiskurant;
import datum.app.clinic.repositoy.ClinicRepository;
import datum.app.clinic.repositoy.PreiskurantRepository;
import datum.app.clinic.service.PreiskurantService;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreiskurantServiceImpl implements PreiskurantService {
    private final PreiskurantRepository preiskurantRepository;
    private final ClinicRepository clinicRepository;

    @Override
    public List<Preiskurant> get(
            HttpServletRequest request,
            long clinicId,
            long employeeId
    ) {
        var preiskurants = preiskurantRepository.findAllByClinicId(clinicId);
        if (preiskurants.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return preiskurants.get();
    }

    @Override
    public Preiskurant get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long preiskurantId
    ) {
        var preiskurant = preiskurantRepository.findById(preiskurantId);
        if (preiskurant.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return preiskurant.get();
    }

    @Override
    public List<Preiskurant> create(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            List<Preiskurant> preiskurants
    ) {
        var clinic = clinicRepository.findById(clinicId);
        if (clinic.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        clinic.get().getPreiskurants().addAll(preiskurants);
        clinicRepository.save(clinic.get());
        return preiskurants;
    }

    @Override
    public Preiskurant update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long preiskurantId,
            MonetaryAmount monetaryAmount
    ) {
        var preiskurant = preiskurantRepository.findByIdAndClinicId(preiskurantId,clinicId);
        if (preiskurant.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        preiskurant.get().setPrice(monetaryAmount);
        preiskurantRepository.save(preiskurant.get());
        return preiskurant.get();
    }

    @Override
    public Preiskurant update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long preiskurantId,
            String description
    ) {
        preiskurantRepository.updateDescriptionByClinicId(preiskurantId, description, clinicId);
        var pre = preiskurantRepository.findById(preiskurantId);
        if (pre.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return pre.get();
    }

    @Override
    public void delete(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long preiskurantId
    ) {
        if(!preiskurantRepository.existsByIdAndClinicId(preiskurantId,clinicId))
            throw new ExceptionApp(404, Message.NOT_FOUND);
        preiskurantRepository.deleteById(preiskurantId);
    }
}
