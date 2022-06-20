#CLEAN CODE
![Alt text](img_1.png?raw=true "Title")
##Clean code là gì ?
- Là code mà người khác có thể dễ dàng:  hiểu, sửa đổi và kiểm tra.


##Tại sao lại cần Clean code ?
- Clean code giúp code dễ bảo trì.
- Giúp thể hiện trình độ lập trình viên.
- Giúp người khác dễ đọc code hơn.
- Xây dựng một quy tắc chung cho bản thân cũng team.
- Khẳng định độ chuyên nghiệp đội ngũ phát triển.

## 4 Mindset chính của CLEAN CODE
### Readable 
- Code có thể đọc hiểu được, khi code phải thể hiện được ý tưởng, mục đích của mình qua code.

```java
public class ReadableExample {
    public void runExample1() {
        // Code không thể hiện được mục đích tên biến thiếu ý nghĩa
        String u = "";
        if (u == "abc124") {
            if (u.length() > 10) {
                //do some thing
            }
        }
    }
    public void runExample2() {
    // Code chưa format gây khó đọc 
    int a = 12;
        for(int i = 0; 
            i<    10;   
            
            
            i ++)
    {
        System.
                out.
                println(" Not format code yet ??!!");
    }}
    //Để format code sử dụng các tổ hợp phím:
    // Mac: CMD + Option + L
    // Windows: Ctrl + Alt + L
}
```
###Maintainable 
- Code có thể chỉnh sửa 1 cách dễ dàng

```java
// Code dài dòng hàm làm nhiều nhiệm vụ cùng 1 lúc khó bảo trì.
public class MaintainableExample {
float calculatePrice(int price, int vat, int sale, int number) {
        int totalPrice = 0;
        String userName = "";
        for (int i = 0; i < 100; i++) {
            if (price > 100) {
                totalPrice = price * number * sale / vat;
            } else if (price > 200) {
                Random rand = new Random();
                int addPrice = (price * number * sale / number / vat) * 3 / 13500 * rand.nextInt() +
                        (price * number * sale / price / vat) * 3 / 1000 * +(price * number * sale / vat / vat) * 3 / 1000 +
                        (vat * number * sale / price / vat) * 3 / 1000;
                sale = sale + addPrice;
            } else if (price < 10) {
                int newPrice = (price * number * vat / number / vat) * 3 / 13500 +
                        (price * number * vat / price / vat) * 3 / 1000 * +(vat * number * sale / vat / vat) * 3 / 1000 +
                        (vat * number * sale * number / price / vat) * 3 / 1000;
                totalPrice = totalPrice + newPrice;
            }
        }
        for (int i = 0; i < 90; i++) {
            if (price > 100) {
                totalPrice = price * number * sale / vat / vat + totalPrice;
            }
        }
        return totalPrice;
    }}
```
###Testable
- Code có thể test được
###Expandable
- Code có thể mở rộng được (được áp dụng nhiều trong thiết kế hệ thống)
```java
public class ExpandableExample {
 //  Khi số lượng các mặt của xúc xắc thay đổi thì phải thay đổi hàm => code khó mở rộng
    public int rollDice(){
        return getRandom(1,6);
    }

    public int rotateWheel(){
        return getRandom(1,12);
    }

    public int getRandom( int firstNumber, int lastNumber ){
        Random rand = new Random();
        return rand.nextInt( lastNumber)+ firstNumber;
    }}

```
##Yếu tố chính ảnh hưởng tới Clean code
- Định danh: Yêu cầu với biến, hàm, lớp hay package phải súc tích, đơn giản, dễ hiểu và thể hiện được ý nghĩa. Tuyệt đối không sử dụng những tên chung chung, khó hiểu hay dễ gây hiểu lầm.
- Hàm: Yêu cầu đối với hàm khi đặt không quá dài, không làm nhiều nhiệm vụ khác nhau, và không có quá nhiều tham số. Đặc biệt, không nên quá lạm dụng ghi chú và sử dụng ghi chú không đúng mục đích.
- Định dạng mã nguồn – Format: đây chính là các khoảng cách lùi đầu dòng.
- Thiết kế và kiến trúc tồi: nó khiến quá trình mở rộng hay thay đổi theo yêu cầu thực tế gặp nhiều khó khăn.
- Thiếu đi các bản kiểm thử: nó ảnh hưởng tới việc không đảm bảo được độ ổn định, chất lượng của mã nguồn.



