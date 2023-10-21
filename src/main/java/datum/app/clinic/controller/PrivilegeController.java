package datum.app.clinic.controller;

import datum.Main;
import datum.app.clinic.model.Privilege;
import datum.app.clinic.service.PrivilegeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/USER/CLINIC/{clinicId}/PRIVILEGE")
@RequiredArgsConstructor
public class PrivilegeController {
    private final PrivilegeService privilegeService;
    //=================================================================================================
    @GetMapping
    public ResponseEntity<List<Privilege>> getPrivilege(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId
    ) {
        return ResponseEntity.ok(
                privilegeService.get(
                        request,
                        Main.parseLong(clinicId)
                )
        );
    }

    @PatchMapping
    public ResponseEntity<List<Privilege>> updatePrivilege(
            HttpServletRequest request,
            @PathVariable("clinicId") String clinicId,
            @RequestBody List<Privilege> privileges
    ) {
        return ResponseEntity.ok(
                privilegeService.update(
                        request,
                        Main.parseLong(clinicId),
                        privileges
                )
        );
    }
    //=================================================================================================
}
