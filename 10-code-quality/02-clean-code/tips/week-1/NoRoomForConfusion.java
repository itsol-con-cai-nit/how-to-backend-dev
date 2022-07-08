package _02_clean_code.tips.week;

import java.util.Date;

// Hãy đặt tên các biến sao cho không thể gây ra bất kỳ sự nhầm lẫn nào
public class NoRoomForConfusion {
    Date expired;
}

class Example {
    Boolean expired;
}

class GoodExample {
    Boolean isExpired;
    Date expirationDate;
}
