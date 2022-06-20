package chapter_2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IntentionRevealingName {
    // chữ d không thể hiện được ý nghĩa gì cả
    Date d;

    // những dòng code đơn giản nhưng vẫn gây khó hiểu do cách đặt tên củ chuối => đặt ra nhiều dấu ?? cho người đọc
    public List<int[]> getThem(List<int[]> theList) {
        List<int[]> list1 = new ArrayList<int[]>();
        for (int[] x : theList)
            if (x[0] == 4)
                list1.add(x);
        return list1;
    }

    //  đã rõ nghĩa hơn, có thể biết được kiểu của biến chi qua cái tên
    Date dateOfBirth;

    // sử dụng thêm từ in days giúp người đọc nhận ra biến này là số ngày chứ không phải ngày cụ thể
    int elapsedTimeInDays;
    int daysSinceCreation;





}
