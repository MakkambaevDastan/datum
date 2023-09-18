package datum.app.clinic.dto;

import datum.app.clinic.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE= Mappers.getMapper(PostMapper.class);
    Post convert(PostDTO postDTO);
}
