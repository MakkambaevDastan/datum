package datum.app.clinic.model;

import datum.app.admin.model.ICD10;
import datum.app.admin.model.Person;
import datum.config.audit.Auditable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
//@Builder
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Jacksonized
@SQLDelete(sql = "update anamnesis set deleted=true where id=?")
@Where(clause = "deleted = false or deleted is null")
@Schema
public class Anamnesis extends Auditable<Long> implements Serializable {
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "anamnesis_icd10",
            joinColumns = {@JoinColumn(name = "anamnesis_id")},
            inverseJoinColumns = {@JoinColumn(name = "icd10_id")}
    )
    private List<ICD10> clinicalDiagnosis = new ArrayList<>(); // Клинический диагноз

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Tooth> tooth = new ArrayList<>(); // Зубная формула

    @JdbcTypeCode(SqlTypes.JSON)
    private List<AnamnesisDetails> anamnesisDetails = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "person_id")
//    private Person person;
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Employee employee;
    private Long personId;
//    private Boolean consent; // Согласие
//    private String complaint; // Жалобы
//    private String allergy; // Аллергологический анамнез:
//    private String diseases; // Перенесённые и сопутствующие заболевания
//    private String medicationsUsed; // Принимаемые лекарственные препараты
//    @Column(length = 2)
//    private Integer temperature; // Температура
//    @Column(length = 3)
//    private Integer pulseRate; // Частота сердечных сокращений
//    private String courseOfDisease; // Развитие настоящего заболевания
//    private String externalExam; // Внешний осмотр
//    private String oralExam; // Осмотр преддверия и полости рта
//    private Occlusion occlusion; // Прикус
//    @Column(length = 2)
//    private Integer hygieneIndex; // Индекс гигиены
//    private Integer caries; // Кариес
//    private Integer fillings; // Пломбы
//    private Integer extracted; // Удалённый
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "differential_diagnosis_id")
//    private List<ICD10> differentialDiagnosis = new ArrayList<>(); // Дифференциальный диагноз
//    private String examPlan; // План осмотра
//    private String surveyData; // Данные обследования
}
