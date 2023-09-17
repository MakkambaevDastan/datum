//package datum.app.clinic.dto;
//
//import datum.app.clinic.model.Schedule;
//import org.mapstruct.InheritInverseConfiguration;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Named;
//import org.mapstruct.factory.Mappers;
//
//import java.time.LocalTime;
//import java.util.List;
//
//@Mapper
//public interface ScheduleMapper {
//    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);
//
//    @Mapping(target = "theStart", source = "theStart", qualifiedByName = "parserStart")
//    @Mapping(target = "theEnd", source="theEnd", qualifiedByName = "parserEnd")
//    @Mapping(target = "id.day", source="day")
//    Schedule convert(ScheduleDTO scheduleDTO);
//    @Named("parserStart")
//    default LocalTime parserStart(String theStart) {
//        return LocalTime.parse(theStart);
//    }
//    @Named("parserEnd")
//    default LocalTime parserEnd(String theEnd) {
//        return LocalTime.parse(theEnd);
//    }
////    @InheritInverseConfiguration
////    ScheduleDTO convert(Schedule schedule);
//
//    @Mapping(target = "theStart", source = "theStart", qualifiedByName = "parserStart")
//    @Mapping(target = "theEnd", source="theEnd", qualifiedByName = "parserEnd")
//    @Mapping(target = "id.day", source="day")
//    List<Schedule> list(List<ScheduleDTO> scheduleDTO);
//}
