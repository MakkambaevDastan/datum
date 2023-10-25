package datum.app.clinic.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum Occlusion {
    DISTAL("Дистальный"),
    MESIAL("Мезиальный"),
    OPEN("Открытый"),
    DEEP("Глубокий"),
    CROSS("Перекрестный"),
    DISTOPIA("Дистопия"),
    DIASTEMA("Диастема");
    private String name;

    Occlusion(String name) {
        this.name = name;
    }
}
