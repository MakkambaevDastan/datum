package datum.app.clinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.EnumMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
//@Entity
public class DayTime implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private LocalTime start;
    private LocalTime end;
}
