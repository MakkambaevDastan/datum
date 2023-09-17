package datum.app.clinic.dto;

import datum.app.clinic.model.Chair;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChairMapper {
    ChairMapper INSTANCE = Mappers.getMapper(ChairMapper.class);

    //    ChairDTO convert(Chair chair);
//    @InheritInverseConfiguration
//    @Mapping(source = "enabled", target = "enabled", defaultValue = "true")
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "deleted", constant = "false")
    Chair convert(ChairDTO chairDTO);
}
