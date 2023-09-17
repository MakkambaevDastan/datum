//package datum.app.clinic.model;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.extern.jackson.Jacksonized;
//import org.hibernate.annotations.CreationTimestamp;
//
//import java.sql.Time;
//import java.sql.Timestamp;
//import java.time.LocalTime;
//import java.time.OffsetDateTime;
//import java.time.OffsetTime;
//import java.util.List;
//import java.util.Map;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Jacksonized
//@IdClass(ScheduleID.class)
//public class Schedule {
//    @Id
//    @Enumerated(EnumType.STRING)
//    private Day day;
//
//    @JsonBackReference
//    @Id
//    @ManyToOne
//    @JoinColumn
//    private Employee employee;
//
//    @Column(columnDefinition = "TIME WITHOUT TIME ZONE")
//    @Temporal(TemporalType.TIME)
//    private LocalTime theStart;
//    @Column(columnDefinition = "TIME WITHOUT TIME ZONE")
//    @Temporal(TemporalType.TIME)
//    private LocalTime theEnd;
//
//    @JsonIgnore
//    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    private OffsetDateTime date;
//}
