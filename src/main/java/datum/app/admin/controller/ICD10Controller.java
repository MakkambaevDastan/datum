package datum.app.admin.controller;

import datum.Main;
import datum.app.admin.dto.ICD10DTO;
import datum.app.admin.mapping.ICD10Mapper;
import datum.app.admin.model.ICD10;
import datum.app.admin.service.ICD10Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ADMIN/ICD10")
@RequiredArgsConstructor
public class ICD10Controller {

    private final ICD10Service icd10Service;

    @GetMapping
    public ResponseEntity<List<ICD10>> getAllIcd10() {
        return ResponseEntity.ok(icd10Service.get());
    }

    @GetMapping("/{icd10Id}")
    public ResponseEntity<ICD10> getIcd10(
            @PathVariable("icd10Id") String icd10Id
    ) {
        return ResponseEntity.ok(icd10Service.get(Main.parseLong(icd10Id)));
    }

    @PostMapping
    public ResponseEntity<List<ICD10>> createIcd10(
            @RequestBody List<ICD10DTO> icd10DTOs
    ) {
        var icd10s = ICD10Mapper.INSTANCE.convert(icd10DTOs);
        return ResponseEntity.ok(icd10Service.create(icd10s));
    }

    @PutMapping
    public ResponseEntity<List<ICD10>> updateIcd10(
            @RequestBody List<ICD10> icd10s
    ) {
        return ResponseEntity.ok(icd10Service.update(icd10s));
    }

    @DeleteMapping("/{icd10Id}")
    public void deleteIcd10(
            @PathVariable("icd10Id") String icd10Id
    ) {
        icd10Service.delete(Main.parseLong(icd10Id));
    }
}
