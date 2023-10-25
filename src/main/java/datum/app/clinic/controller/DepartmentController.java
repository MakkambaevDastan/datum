package datum.app.clinic.controller;

import datum.Main;
import datum.app.clinic.dto.DepartmentDTO;
import datum.app.clinic.mapping.DepartmentMapper;
import datum.app.clinic.model.Department;
import datum.app.clinic.service.DepartmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/CLINIC/{clinicId}/employee/{employeeId}/department")
public class DepartmentController {
    private final DepartmentService departmentService;
    //=================================================================================================
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartment(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId
    ) {
        return ResponseEntity.ok(
                departmentService.get(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId)
                )
        );
    }
    @GetMapping( "/{departmentId}")
    public ResponseEntity<Department> getDepartment(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId
    ) {
        return ResponseEntity.ok(
                departmentService.get(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId)
                )
        );
    }

    @PostMapping
    public ResponseEntity<List<Department>> createDepartment(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @RequestBody List<DepartmentDTO> departmentDTOs
    ) {
        return ResponseEntity.ok(
                departmentService.create(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        DepartmentMapper.INSTANCE.convert(departmentDTOs)
                )
        );
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<Department> updateDepartment(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @RequestBody DepartmentDTO departmentDTO
    ) {
        return ResponseEntity.ok(
                departmentService.update(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        DepartmentMapper.INSTANCE.convert(departmentDTO)
                )
        );
    }

    @DeleteMapping("/{departmentId}")
    public void deleteDepartment(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId
    ) {
        departmentService.delete(
                request,
                Main.parseLong(clinicId),
                Main.parseLong(employeeId),
                Main.parseLong(departmentId)
        );
    }
    //=================================================================================================
}
