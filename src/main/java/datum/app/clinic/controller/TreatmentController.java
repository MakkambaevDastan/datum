package datum.app.clinic.controller;

import datum.Main;
import datum.app.clinic.dto.TreatmentDTO;
import datum.app.clinic.dto.TreatmentUpdate;
import datum.app.clinic.mapping.TreatmentMapper;
import datum.app.clinic.model.Treatment;
import datum.app.clinic.repositoy.PreiskurantRepository;
import datum.app.clinic.service.TreatmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/CLINIC/{clinicId}/employee/{employeeId}/department/{departmentId}/employee/{id}/anamnesis/{anamnesisId}/treatment")
public class TreatmentController {
    private final TreatmentService treatmentService;
    private final PreiskurantRepository preiskurantRepository;

    @GetMapping
    public ResponseEntity<List<Treatment>> getAllTreatment(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id,
            @PathVariable("anamnesisId") String anamnesisId
    ) {
        return ResponseEntity.ok(
                treatmentService.getAll(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        Main.parseLong(id),
                        Main.parseLong(anamnesisId)
                )
        );
    }
    @GetMapping("/{treatmentId}")
    public ResponseEntity<Treatment> getTreatment(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id,
            @PathVariable("anamnesisId") String anamnesisId,
            @PathVariable("treatmentId") String treatmentId
    ) {
        return ResponseEntity.ok(
                treatmentService.get(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        Main.parseLong(id),
                        Main.parseLong(anamnesisId),
                        Main.parseLong(treatmentId)
                )
        );
    }

    @PostMapping("/all")
    public ResponseEntity<Treatment> addTreatment(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id,
            @PathVariable("anamnesisId") String anamnesisId,
            @RequestBody TreatmentDTO treatmentDTO
    ) {
        return ResponseEntity.ok(
                treatmentService.add(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        Main.parseLong(id),
                        Main.parseLong(anamnesisId),
                        TreatmentMapper.INSTANCE.convert(
                                treatmentDTO,
                                preiskurantRepository,
                                Main.parseLong(clinicId)
                        )
                )
        );
    }

    @PostMapping
    public ResponseEntity<List<Treatment>> addAllTreatment(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id,
            @PathVariable("anamnesisId") String anamnesisId,
            @RequestBody List<TreatmentDTO> treatmentDTOs
    ) {
        return ResponseEntity.ok(
                treatmentService.addAll(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        Main.parseLong(id),
                        Main.parseLong(anamnesisId),
                        TreatmentMapper.INSTANCE.convert(
                                treatmentDTOs,
                                preiskurantRepository,
                                Main.parseLong(clinicId)
                        )
                )
        );
    }

    @PatchMapping("/{treatmentId}")
    public ResponseEntity<Treatment> updateTreatment(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id,
            @PathVariable("anamnesisId") String anamnesisId,
            @PathVariable("treatmentId") String treatmentId,
            @RequestBody TreatmentUpdate treatmentUpdate
    ) {
        return ResponseEntity.ok(
                treatmentService.update(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(departmentId),
                        Main.parseLong(id),
                        Main.parseLong(anamnesisId),
                        Main.parseLong(treatmentId),
                        treatmentUpdate
                )
        );
    }

    @DeleteMapping("/{treatmentId}")
    public void deleteTreatment(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("departmentId") String departmentId,
            @PathVariable("id") String id,
            @PathVariable("anamnesisId") String anamnesisId,
            @PathVariable("treatmentId") String treatmentId
    ) {
        treatmentService.delete(
                request,
                Main.parseLong(clinicId),
                Main.parseLong(employeeId),
                Main.parseLong(departmentId),
                Main.parseLong(id),
                Main.parseLong(anamnesisId),
                Main.parseLong(treatmentId)
        );
    }
}
