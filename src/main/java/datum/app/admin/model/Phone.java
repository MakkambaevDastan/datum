package datum.app.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Jacksonized
@Schema
public class Phone implements Serializable {
    @Schema(example = "+996 777 123456")
    private String number;
    @Schema
    private Boolean whatsapp;
    @Schema
    private Boolean telegram;
    @Schema
    private Boolean viber;
}
