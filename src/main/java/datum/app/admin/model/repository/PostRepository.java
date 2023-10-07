package datum.app.admin.model.repository;

import datum.app.admin.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByCode(String head);
    Optional<Post> findByCode(String code);
//    @Modifying
//    @Query(value = "INSERT INTO Post (code, en, ru, kg) " +
//                   "VALUES (:post.code, :post.en, :post.ru, :post.kg) " +
//                   "WHERE NOT EXISTS (SELECT * FROM Post " +
//                   "WHERE code = :post.code AND en = :post.en, AND ru=:post.ru, AND kg=:post.kg)",
//            nativeQuery=true)
//    Post saveIfNotExists(@Param("post") Post post);

//    @Query("SELECT p FROM Post p WHERE p.code = :code AND p.en = :en AND p.ru = :ru AND p.kg = :kg")
//    boolean exists(@Param("code") String code, @Param("en") String en, @Param("ru") String ru, @Param("kg") String kg);
}
