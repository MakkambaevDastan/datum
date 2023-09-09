package datum.app.clinic.service;

import datum.app.clinic.controller.ClinicDTO;
import datum.app.clinic.controller.EmployeeDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface ClinicService {
    public Boolean addClinic(ClinicDTO clinicDTO);
    public Boolean employee(HttpServletRequest request, EmployeeDTO employeeDTO);
//    void addDepartment(DepartmentAddRequest departmentAddRequest);
}
