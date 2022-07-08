package _02_clean_code._01_clean_code;

import java.util.Random;

public class ExpandableExample {
    //  khó mở rộng
    public int rollDice(){
        return getRandom(1,6);
    }

    public int rotateWheel(){
        return getRandom(1,12);
    }

    public int getRandom( int firstNumber, int lastNumber ){
        Random rand = new Random();
        return rand.nextInt( lastNumber)+ firstNumber;
    }
}
