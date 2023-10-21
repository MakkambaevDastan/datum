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
        var dep = departmentRepository.findAllByClinicId(clinicId);
        if (dep.isPresent()) return dep.get();
        throw new ExceptionApp(404, Message.NOT_FOUND);
    }

    @Override
    public Department get(
            HttpServletRequest request,
            long clinicId,
            long employeeId,
            long departmentId
    ) {
        var dep = departmentRepository.findByIdAndClinicId(departmentId, clinicId);
        if (dep.isPresent()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return dep.get();
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
        var dep = departmentRepository.findByIdAndClinicId(departmentId, clinicId);
        if (dep.isPresent()) throw new ExceptionApp(404, Message.NOT_FOUND);
        dep.get().setName(department.getName());
        dep.get().setAddress(department.getAddress());
        dep.get().setPhone(department.getPhone());
        departmentRepository.save(dep.get());
        return dep.get();
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
