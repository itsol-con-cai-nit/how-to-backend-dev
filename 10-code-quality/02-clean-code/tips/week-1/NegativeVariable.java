package tips;

public class NegativeVariable {
    // không tạo ra các biến bolean mang nghĩa tiêu cực
    public void getExample() {
        boolean notGood = false;
        if (!notGood) {
            System.out.println("xyz");
        } else {
            System.out.println("abc");
        }
    }
}
