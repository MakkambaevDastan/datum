package datum.app.clinic.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import datum.app.clinic.model.Day;
import datum.app.clinic.model.EmployeeRole;
//import datum.app.clinic.model.Schedule;
import datum.user.UserDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MapKeyClass;
import jakarta.persistence.MapKeyEnumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
@Jacksonized
public class EmployeeDTO implements Serializable {
    private EmployeeRole role;
    private UserDTO user;
    private Boolean everyWeek;
    private Boolean onEvenWeeks;
//    @MapKeyClass(Day.class)
//    @MapKeyEnumerated(EnumType.STRING)
//    @Enumerated(EnumType.STRING)
    private final Map<String, String> start;
//    @MapKeyClass(Day.class)
//    @MapKeyEnumerated(EnumType.STRING)
//    @Enumerated(EnumType.STRING)
    private final Map<String, String> end;
//    @JsonSetter("start")
//    public void setStart(Day day, String time) {
//        start.put(day, time);
//    }
//    @JsonSetter("end")
//    public void setEnd(Day day, String time) {
//        end.put(day, time);
//    }
//    private final EnumMap<Day, String> start = new EnumMap<>(Map.of(
//            Day.MONDAY, "00:00",
//            Day.TUESDAY, "00:00",
//            Day.WEDNESDAY, "00:00",
//            Day.THURSDAY, "00:00",
//            Day.FRIDAY, "00:00",
//            Day.SATURDAY, "00:00",
//            Day.SUNDAY, "00:00"
//    ));
//    private EnumMap<Day, String> end = new EnumMap<>(Map.of(
//            Day.MONDAY, "00:00",
//            Day.TUESDAY, "00:00",
//            Day.WEDNESDAY, "00:00",
//            Day.THURSDAY, "00:00",
//            Day.FRIDAY, "00:00",
//            Day.SATURDAY, "00:00",
//            Day.SUNDAY, "00:00"
//    ));
}

//    private Map<Day, String> start = new EnumMap<>(Day.class);
//    private String MONDAY;
//    private String TUESDAY;
//    private String WEDNESDAY;
//    private String THURSDAY;
//    private String FRIDAY;
//    private String SATURDAY;
//    private String SUNDAY;
// MONDAY TUESDAY WEDNESDAY THURSDAY FRIDAY SATURDAY SUNDAY