package chapter_6.law_of_demeter;

public class Motobike extends Vehicle {
    public Motobike(Wheel wheel) {
        super(wheel);
    }

    @Override
    boolean canMove() {
        return false;
    }

}
