package datum.app.clinic.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Schema
public class Tooth implements Serializable {
    @Schema(example = "123", description = "icd10Id")
    private Long icd10;
    @Schema(example = "15", description = "FDI World Dental Federation notation")
    private Integer number;
    @Schema(implementation = ToothCondition.class,
            example = "CARIES",
            description = """ 
                CARIES("Кариес"),
                PULPIT("Пульпит"),
                PERIODONTITIS("Периодонтит"),
                DEFECT("Дефект"),
                FILLING("Пломба"),
                CROWN("Искусственная коронка"),
                ARTIFICIAL("Искусственный зуб"),
                TAB("Вкладка"),
                IMPLANT("Имплантат"),
                ROOT("Корень зуба"),
                MISSING("Отсутствующий зуб")
                          """)
    @Enumerated(EnumType.STRING)
    private ToothCondition condition;

    @Schema(implementation = ToothMobility.class,
            example = "I" , description = "Подвижность зубов I, II, III")
    @Enumerated(EnumType.STRING)
    private ToothMobility mobility;
}
