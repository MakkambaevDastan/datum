package datum.app.clinic.implement;

import datum.app.clinic.model.Clinic;
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
        return preiskurantRepository.findAllByClinicId(clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
    }

    @Override
    public Preiskurant get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long preiskurantId
    ) {
        return preiskurantRepository.findById(preiskurantId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
    }

    @Override
    public List<Preiskurant> create(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            List<Preiskurant> preiskurants
    ) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
        clinic.getPreiskurants().addAll(preiskurants);
        clinicRepository.save(clinic);
        return preiskurants;
    }

//    @Override
//    public MonetaryAmount update(
//            HttpServletRequest request,
//            long clinicId,
//            long employeeId,
//            long preiskurantId,
//            MonetaryAmount monetaryAmount
//    ) {
//        Preiskurant preiskurant = preiskurantRepository.findByIdAndClinicId(preiskurantId,clinicId)
//                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
//        preiskurant.setPrice(monetaryAmount);
//        preiskurantRepository.save(preiskurant);
//        return preiskurant.getPrice();
//    }

    @Override
    public String update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long preiskurantId,
            String description
    ) {
        preiskurantRepository.updateDescriptionByClinicId(preiskurantId, description, clinicId);
        return preiskurantRepository.findById(preiskurantId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND))
                .getDescription();
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
