package datum.app.therapy.model.dto;

import datum.app.clinic.model.repositoy.EmployeeRepository;
import datum.app.therapy.model.Anamnesis;
import datum.app.admin.model.ICD10;
import datum.app.therapy.model.repository.AppointmentRepository;
import datum.app.admin.model.repository.ICD10Repository;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AnamnesisMapper {
    AnamnesisMapper INSTANCE = Mappers.getMapper(AnamnesisMapper.class);

    @Mapping(target = "clinicalDiagnosis", source = "clinicalDiagnosis", ignore = true)
    @Mapping(target = "person", source = "person", ignore = true)
    Anamnesis convert(
            AnamnesisDTO anamnesisDTO,
            @Context ICD10Repository icd10Repository
//            @Context EmployeeRepository employeeRepository
    );

    @AfterMapping
    default void getAppointment(
            final @MappingTarget Anamnesis.AnamnesisBuilder anamnesis,
            final AnamnesisDTO anamnesisDTO,
            final @Context ICD10Repository icd10Repository
//            final @Context EmployeeRepository employeeRepository
    ) {
        List<ICD10> icd = new ArrayList<>();
        anamnesisDTO.getClinicalDiagnosis().forEach(
                icd10 -> {
                    if(icd10!=null && icd10>0)
                        icd.add(icd10Repository.findById(icd10).orElseThrow());
                }
        );
        anamnesis.clinicalDiagnosis(icd);
//        anamnesis.employee(employeeRepository.findById(anamnesisDTO.getEmployee()).orElseThrow());
    }
}
