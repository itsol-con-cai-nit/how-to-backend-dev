package chapter_6.law_of_demeter;

public class Bicycle extends Vehicle {

    public Bicycle(Wheel wheel) {
        super(wheel);
    }



    @Override
    boolean canMove() {
        return false;
    }


}
