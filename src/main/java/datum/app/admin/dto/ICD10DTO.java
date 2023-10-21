package datum.app.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class ICD10DTO {
//    private String code; // K00.11
//    private String chapter; // I
//    private String block; //K00-K14
//    private Integer category; // K00
//    private Integer subcategory; // K00.0
//    private Integer item; // K00.00
    private String name;
//    private String description;
    private Boolean dent;
}
