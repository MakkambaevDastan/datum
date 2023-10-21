package datum.app.clinic.service;

import datum.app.clinic.model.Department;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface DepartmentService {
    List<Department> get(
            HttpServletRequest request,
            long clinicId,
            long employeeId
    );
    Department get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId
    );

    List<Department> create(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            List<Department> department
    );

    Department update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            Department department
    );

    void delete(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId
    );
}
