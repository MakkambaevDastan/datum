package datum.app.admin.model.dto;

import datum.app.admin.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE= Mappers.getMapper(PostMapper.class);
    Post convert(PostDTO postDTO);
}
