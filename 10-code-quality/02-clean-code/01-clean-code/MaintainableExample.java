package _02_clean_code._01_clean_code;

import java.util.Random;

public class MaintainableExample {
    // code smell 3
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
    }


}
