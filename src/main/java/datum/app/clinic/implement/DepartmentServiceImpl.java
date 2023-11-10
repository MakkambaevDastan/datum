package datum.app.clinic.implement;

import datum.app.clinic.model.Department;
import datum.app.clinic.repositoy.ClinicRepository;
import datum.app.clinic.repositoy.DepartmentRepository;
import datum.app.clinic.service.DepartmentService;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ClinicRepository clinicRepository;


    @Override
    public List<Department> get(
            HttpServletRequest request,
            long clinicId,
            long employeeId
    ) {
        return departmentRepository.findAllByClinicId(clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
    }

    @Override
    public Department get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId
    ) {
        return departmentRepository.findByIdAndClinicId(departmentId, clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
    }

    @Override
    public List<Department> create(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            List<Department> department
    ) {
        var clinic = clinicRepository.findById(clinicId);
        if (clinic.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        clinic.get().getDepartments().addAll(department);
        departmentRepository.saveAll(department);
        return department;
    }

    @Override
    public Department update(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId,
            Department department
    ) {
        Department dep = departmentRepository.findByIdAndClinicId(departmentId, clinicId)
                .orElseThrow(() -> new ExceptionApp(404, Message.NOT_FOUND));
        dep.setName(department.getName());
        dep.setAddress(department.getAddress());
        return departmentRepository.save(dep);
    }

    @Override
    public void delete(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId
    ) {
        if (!departmentRepository.existsByIdAndClinicId(departmentId, clinicId))
            throw new ExceptionApp(404, Message.NOT_FOUND);
        departmentRepository.deleteById(departmentId);
    }
}
