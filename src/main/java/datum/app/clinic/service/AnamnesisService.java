package datum.app.clinic.service;

import datum.app.clinic.dto.AnamnesisDTO;
import datum.app.clinic.model.Anamnesis;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AnamnesisService {
    List<Anamnesis> get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id
    );
    Anamnesis get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId
    );
    Anamnesis create(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            Anamnesis anamnesis
    );
//    Anamnesis update(
//            HttpServletRequest request,
//            long clinicId,
//            long employeeId,
//            long departmentId,
//            long id,
//            long anamnesisId,
//            AnamnesisDTO anamnesisDTO
//    );
    void delete(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            long id,
            long anamnesisId
    );
}
