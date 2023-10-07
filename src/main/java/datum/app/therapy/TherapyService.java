package datum.app.therapy;

import datum.app.therapy.model.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface TherapyService {


    Object preiskurant(HttpServletRequest request, HttpServletResponse response,Long id, List<PreiskurantDTO> preiskurantDTOs);


    Object appointment(
            HttpServletRequest request,
            HttpServletResponse response,
            AppointmentDTO appointmentDTO
    );

    Object anamnesis(HttpServletRequest request, HttpServletResponse response, AnamnesisDTO anamnesisDTO);

}
