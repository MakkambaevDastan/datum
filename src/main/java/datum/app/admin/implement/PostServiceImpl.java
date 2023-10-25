package datum.app.admin.implement;

import datum.app.admin.model.Post;
import datum.app.admin.repository.PostRepository;
import datum.app.admin.service.PostService;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public List<Post> get() {
        return postRepository.findAll();
    }

    @Override
    public Post get(long postId) {
        var post = postRepository.findById(postId);
        if (post.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return post.get();
    }

    @Override
    public List<Post> create(List<Post> posts) {
        return postRepository.saveAll(posts);
    }

    @Override
    public List<Post> update(List<Post> posts) {
        return postRepository.saveAll(posts);
    }

    @Override
    public void delete(long postId) {
        postRepository.deleteById(postId);
    }

    @PostConstruct
    private void run() {
        List<Post> posts = new ArrayList<>();
        if (!postRepository.existsByCode("OWNER"))
            posts.add(Post.builder()
                    .code("OWNER")
                    .en("Owner")
                    .ru("Владелец")
                    .kg("Ээси")
                            .deleted(false)
                    .build());
        if (!postRepository.existsByCode("HEAD"))
            posts.add(Post.builder()
                    .code("HEAD")
                    .en("Chief physician")
                    .ru("Главный врач")
                    .kg("Башкы дарыгер")
                    .deleted(false)
                    .build());
        if (!postRepository.existsByCode("ADMIN"))
            posts.add(Post.builder()
                    .code("ADMIN")
                    .en("Administrator")
                    .ru("Администратор")
                    .kg("Башкаруучу")
                    .deleted(false)
                    .build());
        if (!postRepository.existsByCode("DENTIST"))
            posts.add(Post.builder()
                    .code("DENTIST")
                    .en("Dentist")
                    .ru("Врач стоматолог")
                    .kg("Тиш доктур")
                    .deleted(false)
                    .build());
        if (!postRepository.existsByCode("DENTAL_ASSISTANT"))
            posts.add(Post.builder()
                    .code("DENTAL_ASSISTANT")
                    .en("Dental assistant")
                    .ru("Ассистент стоматолога")
                    .kg("Тиш доктур жардамчысы")
                    .deleted(false)
                    .build());
        if (!postRepository.existsByCode("NURSE"))
            posts.add(Post.builder()
                    .code("NURSE")
                    .en("Nurse")
                    .ru("Медицинская сестра")
                    .kg("Медайым")
                    .deleted(false)
                    .build());
        postRepository.saveAll(posts);
        System.out.println("Сохранение конечных точек");
    }
}
