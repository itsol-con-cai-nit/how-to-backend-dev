package chapter_2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AvoidDisinformation {
    // tên viết tắt dễ gây hiểu nhầm
    Date dob;

    // có thể đặt tên như thế này vì thực sự là List
    List<String> phoneNumberList;

    // không đặt tên như thế này vì kiểu dữ liệu là ArrayList không phải List dễ gây sai sót
    ArrayList<String> nameList;

    // ok
    ArrayList<String> names;
    ArrayList<String> accountGroup;

    // không đặt tên gần giống nhau dễ bị nhầm giữa 2 biến
    String XYZControllerForEfficientHandlingOfStrings;
    String XYZControllerForEfficientStorageOfStrings;


}
