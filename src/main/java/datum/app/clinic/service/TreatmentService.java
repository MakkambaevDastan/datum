package datum.app.clinic.service;

import datum.app.clinic.dto.TreatmentUpdate;
import datum.app.clinic.model.Treatment;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface TreatmentService  {
    Treatment get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId,
            long treatmentId
    );
    List<Treatment> getAll(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId
    );

    Treatment add(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId,
            Treatment treatment
    );
    List<Treatment> addAll(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId,
            List<Treatment> treatments
    );
    Treatment update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId,
            long treatmentId,
            TreatmentUpdate treatmentUpdate
    );
    void delete(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId,
            long treatmentId
    );
}
