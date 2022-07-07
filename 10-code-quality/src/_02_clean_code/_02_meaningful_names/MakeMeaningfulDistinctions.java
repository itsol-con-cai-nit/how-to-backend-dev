package _02_clean_code._02_meaningful_names;

public class MakeMeaningfulDistinctions {
    // tên khiến người khác ngứa tay :v
    String klass;

    // a1, a2 dùng để làm gì ?
    public static void copyChars(char a1[], char a2[]) {
        for (int i = 0; i < a1.length; i++) {
            a2[i] = a1[i];
        }
    }

    // Tránh đặt tên khác nhau nhưng về bản chất mang cùng 1 ý nghĩa vd về Product

    // Không sử dụng các yếu tố gây nhiễu trong tên, name không phải là 1 số,
    // nếu là 1 số thì phá vỡ nguyên tắc Tránh sai lệch thông tin
    String nameString;

}
