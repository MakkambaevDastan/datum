package datum.app.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@AllArgsConstructor
@Jacksonized
public class PostDTO {
//    private Long id;
    private String code;
    private String en;
    private String ru;
    private String kg;
}
