package datum.app.clinic.controller;

import datum.Main;
import datum.app.admin.repository.ICD10Repository;
import datum.app.clinic.dto.AnamnesisDTO;
import datum.app.clinic.mapping.AnamnesisMapper;
import datum.app.clinic.model.Anamnesis;
import datum.app.clinic.service.AnamnesisService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/CLINIC/{clinicId}/employee/{employeeId}/department/{departmentId}/employee/{id}/anamnesis")
public class AnamnesisController {
    private final AnamnesisService anamnesisService;
    private final ICD10Repository icd10Repository;

    @GetMapping
    public ResponseEntity<List<Anamnesis>> getAnamnesis(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id
    ) {
        return ResponseEntity.ok(
                anamnesisService.get(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        Main.parseLong(id)
                )
        );
    }

    @GetMapping("/{anamnesisId}")
    public ResponseEntity<Anamnesis> getAnamnesis(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id,
            @PathVariable("anamnesisId") String anamnesisId
    ) {
        return ResponseEntity.ok(
                anamnesisService.get(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        Main.parseLong(id),
                        Main.parseLong(anamnesisId)
                )
        );
    }

    @PostMapping
    public ResponseEntity<Anamnesis> createAnamnesis(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id,
            @RequestBody AnamnesisDTO anamnesisDTO
    ) {
        return ResponseEntity.ok(
                anamnesisService.create(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        Main.parseLong(id),
                        AnamnesisMapper.INSTANCE.convert(anamnesisDTO, icd10Repository)
                )
        );
    }

    @PutMapping("/{anamnesisId}")
    public ResponseEntity<Anamnesis> updateAnamnesis(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id,
            @PathVariable("anamnesisId") String anamnesisId,
            @RequestBody AnamnesisDTO anamnesisDTO
    ) {
        return ResponseEntity.ok(
                anamnesisService.update(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        Main.parseLong(id),
                        Main.parseLong(anamnesisId),
                        AnamnesisMapper.INSTANCE.convert(anamnesisDTO, icd10Repository)
                )
        );
    }

    @DeleteMapping("/{anamnesisId}")
    public void deleteAnamnesis(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id,
            @PathVariable("anamnesisId") String anamnesisId
    ) {
        anamnesisService.delete(
                request,
                Main.parseLong(clinicId),
                Main.parseLong(employeeId),
                Main.parseLong(departmentId),
                Main.parseLong(id),
                Main.parseLong(anamnesisId)
        );
    }
}
