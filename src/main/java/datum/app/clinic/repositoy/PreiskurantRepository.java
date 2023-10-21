package datum.app.clinic.repositoy;

import datum.app.clinic.model.MonetaryAmount;
import datum.app.clinic.model.Preiskurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PreiskurantRepository extends JpaRepository<Preiskurant, Long> {
    @Query(value = "SELECT * FROM preiskurant WHERE preiskurant.clinic_id = :clinicId", nativeQuery = true)
    Optional<List<Preiskurant>> findAllByClinicId(Long clinicId);
    @Query(value = """
        SELECT * FROM preiskurant
        WHERE preiskurant.clinic_id = :clinicId
        AND preiskurant.id = :preiskurantId
        """, nativeQuery = true)
    Optional<Preiskurant> findByIdAndClinicId(Long preiskurantId, Long clinicId);
    @Query(value = """
        SELECT CASE WHEN count(preiskurant.id) > 0 THEN true ELSE false END
        FROM preiskurant
        WHERE preiskurant.clinic_id = :clinicId
        AND preiskurant.id = :preiskurantId
        """, nativeQuery = true)
    boolean existsByIdAndClinicId(Long preiskurantId, Long clinicId);
    @Query(value = """
        UPDATE preiskurant
        SET preiskurant.description = :description
        WHERE preiskurant.id = :preiskurantId AND preiskurant.clinic_id = :clinicId
    """, nativeQuery = true)
    void updateDescriptionByClinicId(Long preiskurantId, String description, Long clinicId);
}
