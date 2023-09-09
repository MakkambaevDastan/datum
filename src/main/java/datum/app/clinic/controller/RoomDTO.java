package datum.app.clinic.controller;

import datum.app.clinic.model.Chair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private String name;
    private List<ChairDTO> chairs;
}
