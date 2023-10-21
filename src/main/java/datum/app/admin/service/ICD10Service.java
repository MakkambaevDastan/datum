package datum.app.admin.service;

import datum.app.admin.model.ICD10;

import java.util.List;

public interface ICD10Service {
    List<ICD10> getDental();
    List<ICD10> get();
    ICD10 get(long icd10Id);

    List<ICD10> create(List<ICD10> icd10s);

    List<ICD10> update(List<ICD10> icd10s);

    void delete(long icd10Id);
}
