package chapter_6.law_of_demeter;

public abstract class Vehicle {
  private Wheel  wheel;
  private Engine engine;

    public Vehicle(Wheel wheel) {
        this.wheel = wheel;
    }

    public Wheel getWheel() {
        return wheel;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setWheel(Wheel wheel) {
        this.wheel = wheel;
    }

    public void move(){
        System.out.println("move");
    }

    abstract boolean canMove();
}
