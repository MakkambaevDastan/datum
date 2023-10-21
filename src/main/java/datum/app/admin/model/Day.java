package datum.app.admin.model;

//import lombok.*;

import java.io.Serializable;
//import java.time.LocalTime;

//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@RequiredArgsConstructor
public enum Day implements Serializable {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
//    private final LocalTime[] time;
//    private Day(LocalTime[] myArray) {
//        this.time = myArray;
//    }

//    MONDAY("Понедельник"),
//    TUESDAY("Вторник"),
//    WEDNESDAY("Среда"),
//    THURSDAY("Четверг"),
//    FRIDAY("Пятница"),
//    SATURDAY("Суббота"),
//    SUNDAY("Воскресенье");
//    private String name;
//
////    Day(String text){name = text;}
//
//    public String getName(){return name;}
//
//    public static Day fromName(String text){
//        for(Day r : Day.values()){
//            if(r.getName().equals(text)){
//                return r;
//            }
//        }
//        throw new IllegalArgumentException();
//    }
}
