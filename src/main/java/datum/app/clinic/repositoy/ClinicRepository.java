package datum.app.clinic.repositoy;

import datum.app.clinic.model.Clinic;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    Optional<List<Clinic>> findAllByCreatedBy(Long createdBy);
    Optional<Clinic> findByIdAndCreatedBy(Long clinicId, Long createdBy);
    void deleteByIdAndCreatedBy(Long clinicId, Long createdBy);

    @Query(value ="""
            SELECT clinic.*
            FROM clinic
               INNER JOIN employee ON employee.user_id=clinic.user_id
               INNER JOIN post ON post.id=employee.post_id
            WHERE clinic.id=:clinicId AND clinic.user_id=:userId AND post.code=:post
               """, nativeQuery = true)
    Optional<Clinic> selectWhereIdAndUserId(Long clinicId, Long userId, String post);
    @Query(value = "SELECT * FROM clinic WHERE clinic.user_id=:userId", nativeQuery = true)
    Optional<List<Clinic>> findAllByUserId(Long userId);

    @Query(value = "SELECT * FROM clinic WHER clinic.id=:clinicId AND clinic.user_id=:userId", nativeQuery = true)
    Optional<Clinic> countByIdAndUserId(Long clinicId, Long userId);
    @Modifying
    @Query(value = "UPDATE clinic SET clinic.name = :name " +
                   "WHERE clinic.id=:clinicId AND user_id=:userId", nativeQuery = true)
    void updateClinic(Long clinicId, Long userId, String name);
    @Query(value = "SELECT name FROM clinic WHERE clinic.id=:clinicId", nativeQuery = true)
    Optional<String> findNameById(Long clinicId);
//    Boolean existsClinicByIdAndUserId()
//    @Query(value = "SELECT COUNT(*) FROM clinic WHERE  ", nativeQuery = true)
//    boolean existsByIdAndUserId(Long clinicId, Long userId);
//    @Query(value = """
//SELECT COUNT(*) FROM clinic
//WHERE clinic.id=:clinicId
//""", nativeQuery = true)
//    int checkUser(Long clinicId, Long userId, String endpoint, String method);

}
