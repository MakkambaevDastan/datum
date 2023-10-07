package datum.app.therapy.model;

public enum ToothCondition {
    CARIES("Кариес"),
    PULPIT("Пульпит"),
    PERIODONTITIS("Периодонтит"),
    DEFECT("Дефект"),
    FILLING("Пломба"),
    CROWN("Искусственная коронка"),
    ARTIFICIAL("Искусственный зуб"),
    TAB("Вкладка"),
    IMPLANT("Имплантат"),
    ROOT("Корень зуба"),
    MISSING("Отсутствующий зуб");
    private final String name;
     ToothCondition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
