package datum.app.admin.service;

import datum.app.admin.model.Post;

import java.util.List;

public interface PostService {
    List<Post> get();

    Post get(long postId);

    List<Post> create(List<Post> posts);

    void delete(long postId);

    List<Post> update(List<Post> posts);
}
