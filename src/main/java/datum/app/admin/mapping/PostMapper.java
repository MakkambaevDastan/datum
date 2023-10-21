package datum.app.admin.mapping;

import datum.app.admin.dto.PostDTO;
import datum.app.admin.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE= Mappers.getMapper(PostMapper.class);
    Post convert(PostDTO postDTO);
    List<Post> convert(List<PostDTO> postDTOs);
}
