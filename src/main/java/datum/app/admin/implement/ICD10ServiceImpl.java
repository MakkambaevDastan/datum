package datum.app.admin.implement;

import datum.Main;
import datum.app.admin.model.ICD10;
import datum.app.admin.repository.ICD10Repository;
import datum.app.admin.service.ICD10Service;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import datum.lib.CyrillicLatinConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ICD10ServiceImpl implements ICD10Service {
    private final ICD10Repository icd10Repository;

    @Override
    public List<ICD10> getDental() {
        Optional<List<ICD10>> icd10s = icd10Repository.findAllByBlockAndCategoryBetween("K", 0, 14);
        if (icd10s.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return icd10s.get();
    }

    @Override
    public List<ICD10> get() {
        return icd10Repository.findAll();
    }

    @Override
    public ICD10 get(long icd10Id) {
        var icd = icd10Repository.findById(icd10Id);
        if (icd.isEmpty()) throw new ExceptionApp(404, Message.NOT_FOUND);
        return icd.get();

    }

    @Override
    public List<ICD10> create(List<ICD10> icd10s) {
        Function<String, String> cyrillicToLatin = CyrillicLatinConverter::cyrilicToLatin;
        icd10s.forEach(icd10 -> {
            String code = icd10.getName().substring(0, 6).toUpperCase().trim();
            if (!code.substring(3, 4).equals(".") || !Main.isInteger(code.substring(5)))
                throw new ExceptionApp(400, "Не правильный код MKБ-10: " + code);
            if (!(code.charAt(0) >= 'A' && code.charAt(0) <= 'Z'))
                code = cyrillicToLatin.apply(code);
            String name = icd10.getName().substring(6).trim();
                icd10.setCode(code);
                String block = code.substring(0, 1).toUpperCase();
                int category = Integer.parseInt(code.substring(1, 3));
                int subcategory = Integer.parseInt(code.substring(4, 5));
                int item = Integer.parseInt(code.substring(5, 6));

                ICD10 icd = icd10Repository.findFirstByBlock(block);
                icd10.setChapter(icd.getChapter());
                icd10.setBlock(block);
                icd10.setCategory(category);
                icd10.setSubcategory(subcategory);
                icd10.setItem(item);
                icd10.setName(name);
        });
        icd10Repository.saveAll(icd10s);
        return icd10s;
    }

    @Override
    public List<ICD10> update(List<ICD10> icd10s) {
        return icd10Repository.saveAll(icd10s);
    }

    @Override
    public void delete(long icd10Id) {
        icd10Repository.deleteById(icd10Id);
    }
}
