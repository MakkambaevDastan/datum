package datum.app.clinic.model;

//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
//import io.hypersistence.utils.hibernate.type.array.EnumArrayType;
//import io.hypersistence.utils.hibernate.type.array.internal.AbstractArrayType;
//import io.hypersistence.utils.hibernate.type.json.JsonType;
//import io.hypersistence.utils.hibernate.type.range.Range;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.extern.jackson.Jacksonized;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.Type;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.sql.Time;
//import java.sql.Timestamp;
//import java.time.Instant;
//import java.time.LocalTime;
//import java.time.OffsetDateTime;
//import java.time.OffsetTime;
//import java.util.EnumMap;
//import java.util.List;
//import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import datum.app.admin.model.Day;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

//@Data
@Builder
@NoArgsConstructor
//@AllArgsConstructor
@Jacksonized
@Getter
@Setter
@Schema
public class Schedule implements Serializable {
    @Schema
    private final EnumMap<Day, DayTime> week = new EnumMap<>(Map.of(
            Day.MONDAY, new DayTime(),
            Day.TUESDAY, new DayTime(),
            Day.WEDNESDAY, new DayTime(),
            Day.THURSDAY, new DayTime(),
            Day.FRIDAY, new DayTime(),
            Day.SATURDAY, new DayTime(),
            Day.SUNDAY, new DayTime()
    ));
    @Schema
    private final EnumMap<Day, DayTime> secondWeek = new EnumMap<>(Map.of(
            Day.MONDAY, new DayTime(),
            Day.TUESDAY, new DayTime(),
            Day.WEDNESDAY, new DayTime(),
            Day.THURSDAY, new DayTime(),
            Day.FRIDAY, new DayTime(),
            Day.SATURDAY, new DayTime(),
            Day.SUNDAY, new DayTime()
    ));
//    public  Schedule() {
//        this.week = new EnumMap<>(Map.of(
//                Day.MONDAY, new DayTime(),
//                Day.TUESDAY, new DayTime(),
//                Day.WEDNESDAY, new DayTime(),
//                Day.THURSDAY, new DayTime(),
//                Day.FRIDAY, new DayTime(),
//                Day.SATURDAY, new DayTime(),
//                Day.SUNDAY, new DayTime()
//        ));
//        this.secondWeek = new EnumMap<>(Map.of(
//                Day.MONDAY, new DayTime(),
//                Day.TUESDAY, new DayTime(),
//                Day.WEDNESDAY, new DayTime(),
//                Day.THURSDAY, new DayTime(),
//                Day.FRIDAY, new DayTime(),
//                Day.SATURDAY, new DayTime(),
//                Day.SUNDAY, new DayTime()
//        ));
//    }

//    private EnumMap<Day, DayTime> week = new EnumMap<>(Day.class);
//    private final EnumMap<Day, DayTime> week = new EnumMap(Day.class) {{
//        put(Day.MONDAY, new DayTime());
//        put(Day.TUESDAY, new DayTime());
//        put(Day.WEDNESDAY, new DayTime());
//        put(Day.THURSDAY, new DayTime());
//        put(Day.FRIDAY, new DayTime());
//        put(Day.SATURDAY, new DayTime());
//        put(Day.SUNDAY, new DayTime());
//    }};
//    @Schema
//    private final EnumMap<Day, DayTime> week = new EnumMap<>(Map.of(
//            Day.MONDAY, new DayTime(),
//            Day.TUESDAY, new DayTime(),
//            Day.WEDNESDAY, new DayTime(),
//            Day.THURSDAY, new DayTime(),
//            Day.FRIDAY, new DayTime(),
//            Day.SATURDAY, new DayTime(),
//            Day.SUNDAY, new DayTime()
//    ));
//    @Schema
//    private final EnumMap<Day, DayTime> secondWeek = new EnumMap<>(Map.of(
//            Day.MONDAY, new DayTime(),
//            Day.TUESDAY, new DayTime(),
//            Day.WEDNESDAY, new DayTime(),
//            Day.THURSDAY, new DayTime(),
//            Day.FRIDAY, new DayTime(),
//            Day.SATURDAY, new DayTime(),
//            Day.SUNDAY, new DayTime()
//    ));


}
