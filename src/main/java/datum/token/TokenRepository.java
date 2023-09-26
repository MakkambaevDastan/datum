package datum.token;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("select t from Token t inner join User u \n on t.user.id = u.id \n" +
           "where u.id = :id and (t.expired = false or t.revoked = false) \n")
    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByAccessToken(String token);
    @Query("select t from Token t where t.refreshToken = :token")
    Optional<Token> findByByRefreshToken(String token);

    //  @Modifying
    @Query("update Token u set u.accessToken = :accessToken where u.refreshToken = :refreshToken")
    Boolean updateAccessTokenByRefreshToken(String accessToken, String refreshToken);
}
