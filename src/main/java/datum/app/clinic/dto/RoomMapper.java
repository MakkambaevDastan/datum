package datum.app.clinic.dto;

import datum.app.clinic.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ChairMapper.class})
//@Mapper
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

//    RoomDTO convert(Room room);

//    @InheritInverseConfiguration
@Mapping(target = "enabled", constant = "true")
@Mapping(target = "deleted", constant = "false")
    Room convert(RoomDTO roomDTO);
}
