package datum.app.therapy.model;

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
