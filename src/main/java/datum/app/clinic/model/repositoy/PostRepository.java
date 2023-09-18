package datum.app.clinic.model.repositoy;

import datum.app.clinic.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByCode(String head);
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
