package datum.authenticate;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//@Repository
//@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<Clinic> findAllClinicsByIdAndCreatedOnBetween(Long id, Date start, Date end);

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByEmailIgnoreCaseAndCode(String email, Integer code);
//    Optional<User> findByEmailIgnoreCaseAndCodeIsNull(String email);

    boolean existsByEmailIgnoreCase(String email);
//    boolean existsByEmailIgnoreCaseAndCodeIsNull(String email);
//    boolean existsByEmailIgnoreCaseAndCodeIsNullAndLockedFalse(String email);

//      @Transactional
    @Modifying
    @Query("UPDATE User a SET a.enabled = TRUE, a.locked=FALSE, a.code = NULL WHERE a.email = ?1 AND a.code = ?2")
    void updateUserByEmailAndCode(String email, Integer code);

//    @Transactional
        @Modifying
    @Query("UPDATE User a SET a.enabled = TRUE, a.locked=FALSE, a.code = NULL WHERE a.email = ?1")
    void enableUserByEmail(String email);

//@Transactional
        @Modifying
    @Query("UPDATE User u SET u.password = null WHERE u.email = ?1 AND u.code = ?2")
    boolean updateUserPasswordByEmailIgnoreCaseAndCode(String email, Integer code);

//    @Transactional
        @Modifying
    @Query("UPDATE User u SET u.password = ?1 WHERE u.email = ?2")
    boolean updateUserPasswordByEmailIgnoreCase(String password, String email);

//    @Transactional
        @Modifying
    @Query("UPDATE User u SET u.password = ?1 WHERE u.email = ?2 AND u.password IS NULL")
    boolean updatePasswordByEmail(String password, String email);
}
