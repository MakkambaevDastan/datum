package datum.app.clinic.service;

import datum.app.clinic.model.MonetaryAmount;
import datum.app.clinic.model.Preiskurant;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PreiskurantService {
    List<Preiskurant> get(
            HttpServletRequest request,
            long clinicId,
            long employeeId
    );
    Preiskurant get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long preiskurantId
    );

    List<Preiskurant> create(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            List<Preiskurant> preiskurants
    );
//    MonetaryAmount update(
//            HttpServletRequest request,
//            long clinicId,
//            long employeeId,
//            long preiskurantId,
//            MonetaryAmount monetaryAmount
//    );
    String update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long preiskurantId,
            String description
    );
    void delete(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long preiskurantId
    );
}
