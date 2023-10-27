package datum.app.clinic.controller;

import datum.Main;
import datum.app.clinic.dto.PreiskurantDTO;
import datum.app.clinic.mapping.PreiskurantMapper;
import datum.app.clinic.model.MonetaryAmount;
import datum.app.clinic.model.Preiskurant;
import datum.app.clinic.service.PreiskurantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/CLINIC/{clinicId}/employee/{employeeId}/preiskurant")
public class PreiskurantController {
    private final PreiskurantService preiskurantService;


    //=================================================================================================
    @GetMapping
    public ResponseEntity<List<Preiskurant>> getPreiskurant(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId
    ) {
        return ResponseEntity.ok(
                preiskurantService.get(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId)
                )
        );
    }
    @GetMapping({"/{preiskurantId}"})
    public ResponseEntity<Preiskurant> getPreiskurant(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("preiskurantId") String preiskurantId
    ) {
        return ResponseEntity.ok(
                preiskurantService.get(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(preiskurantId)
                )
        );
    }
    @PostMapping
    public ResponseEntity<List<Preiskurant>> createPreiskurant(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @RequestBody List<PreiskurantDTO> preiskurantDTOs
    ) {
        return ResponseEntity.ok(
                preiskurantService.create(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        PreiskurantMapper.INSTANCE.convert(preiskurantDTOs)
                )
        );
    }

//    @PutMapping("/{preiskurantId}/monetaryAmount")
//    public ResponseEntity<MonetaryAmount> updatePreiskurant(
//            HttpServletRequest request,
//            @PathVariable("clinicId") String clinicId,
//            @PathVariable("employeeId") String employeeId,
//            @PathVariable("preiskurantId") String preiskurantId,
//            @RequestBody MonetaryAmount monetaryAmount
//    ) {
//        return ResponseEntity.ok(
//                preiskurantService.update(
//                        request,
//                        Main.parseLong(clinicId),
//                        Main.parseLong(employeeId),
//                        Main.parseLong(preiskurantId),
//                        monetaryAmount
//                )
//        );
//    }

    @PutMapping("/{preiskurantId}/description")
    public ResponseEntity<String> updatePreiskurant(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("preiskurantId") String preiskurantId,
            @RequestBody String description
    ) {
        return ResponseEntity.ok(
                preiskurantService.update(
                        request,
                        Main.parseLong(clinicId),
                        Main.parseLong(employeeId),
                        Main.parseLong(preiskurantId),
                        description
                )
        );
    }

    @DeleteMapping("/{preiskurantId}")
    public void deletePreiskurant(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @PathVariable("employeeId") String employeeId,
            @PathVariable("preiskurantId") String preiskurantId
    ) {
        preiskurantService.delete(
                request,
                Main.parseLong(clinicId),
                Main.parseLong(employeeId),
                Main.parseLong(preiskurantId)
        );
    }
    //=================================================================================================

}
