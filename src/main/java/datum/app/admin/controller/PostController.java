package datum.app.admin.controller;

import datum.Main;
import datum.app.admin.dto.PostDTO;
import datum.app.admin.mapping.PostMapper;
import datum.app.admin.model.Post;
import datum.app.admin.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ADMIN/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping
    public ResponseEntity<List<Post>> getPost() {
        return ResponseEntity.ok(postService.get());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getAllPost(
            @PathVariable("postId") String postId
    ) {
            return ResponseEntity.ok(
                    postService.get(
                            Main.parseLong(postId)
                    )
            );
    }

    @PostMapping
    public ResponseEntity<List<Post>> createPost(
            @RequestBody List<PostDTO> postDTOs
    ) {
        var posts = PostMapper.INSTANCE.convert(postDTOs);
        return ResponseEntity.ok(postService.create(posts));
    }

    @PutMapping
    public ResponseEntity<List<Post>> updatePost(
            @RequestBody List<Post> posts
    ) {
        return ResponseEntity.ok(postService.update(posts));
    }

    @DeleteMapping("/{postId}")
    public void deletePost(
            @PathVariable("postId") String postId
    ) {
            postService.delete(Main.parseLong(postId));
    }

}
