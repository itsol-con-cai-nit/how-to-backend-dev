package chapter_6.law_of_demeter;

public class Bicycle extends Vehicle {

    public Bicycle(Wheel wheel) {
        super(wheel);
    }

    @Override
    boolean canMove() {
        if(this.getWheel().getTension()>0.7){
            return true;
        }else
        return false;
    }

}
