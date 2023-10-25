package datum.app.clinic.service;

import datum.app.clinic.model.Privilege;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PrivilegeService {
    Privilege getPrivilege(HttpServletRequest request, long clinicId, long employeeId);
    List<Privilege> get(HttpServletRequest request, long clinicId);

    List<Privilege> update(HttpServletRequest request, long clinicId, List<Privilege> privileges);
}
