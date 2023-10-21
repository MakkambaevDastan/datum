package datum.app.admin.repository;

import datum.app.admin.model.ICD10;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICD10Repository extends JpaRepository<ICD10, Long> {
//    @Query("select icd10 from ICD10 icd10 where icd10.block = ?1 fetch first 1 rows only")
    ICD10  findFirstByBlock(String block);

    Optional<List<ICD10>> findAllByBlockAndCategoryBetween(String block, Integer down, Integer up);
//    ICD10 findTop1ByCode(String code);
//    @Query("select icd10 from ICD10 icd10 where icd10.block = ?1 and icd10.category = ?2 and icd10.subcategory = ?3")
//    List<ICD10> findICD10ByBlockAndAndCategoryAndSubcategory(String block, Integer category, Integer subcategory);


}
