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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import datum.app.admin.model.Day;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Entity
@Jacksonized
@Schema
public class Schedule implements Serializable {

//    @JsonProperty("week")
//    @Schema(example = "99.96")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private EnumMap<Day, Map<String, String>> week = new EnumMap<>(Day.class);

//    @JsonProperty("secondWeek")
//    @Schema(example = "99.96")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private EnumMap<Day, Map<String, String>> secondWeek = new EnumMap<>(Day.class);
}
