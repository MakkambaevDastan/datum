package datum.app.clinic.model.repositoy;

import datum.app.clinic.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    @Query(value = "SELECT clinic.* " +
                   "FROM clinic " +
                   "    INNER JOIN employee ON employee.user_id=clinic.user_id " +
                   "    INNER JOIN post ON post.id=employee.post_id " +
                   "WHERE clinic.id=:id AND clinic.user_id=:userId AND post.code=:post", nativeQuery = true)
    Optional<Clinic> selectWhereIdAndUserId(Long id, Long userId, String post);

}
