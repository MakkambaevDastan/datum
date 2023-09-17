package datum.app.clinic.model;

import lombok.*;
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
@RequiredArgsConstructor
public enum Day {
MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
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
