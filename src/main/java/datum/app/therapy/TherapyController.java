package datum.app.therapy;

import datum.app.therapy.model.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/therapy")
public class TherapyController {
    private final TherapyService therapyService;

    @PostMapping("/{id}/preiskurant")
    public ResponseEntity<Object> preiskurant(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("id") String id,
            @RequestBody List<PreiskurantDTO> preiskurantDTOs
    ) {
        return ResponseEntity.ok(therapyService.preiskurant( request, response, Long.parseLong(id), preiskurantDTOs ));
    }
    @PostMapping("/appointment")
    public ResponseEntity<Object> appointment(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody AppointmentDTO appointmentDTO
    ){
        return ResponseEntity.ok(therapyService.appointment( request, response, appointmentDTO ));
    }

    @PostMapping("/anamnesis")
    public ResponseEntity<Object> anamnesis(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody AnamnesisDTO anamnesisDTO
    ){
        return ResponseEntity.ok(therapyService.anamnesis( request, response, anamnesisDTO ));
    }

}
