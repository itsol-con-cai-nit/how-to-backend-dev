package chapter_6.law_of_demeter;

public class Wheel {
    private double tension;

    public double getTension() {
        return tension;
    }

    public void setTension(double tension) {
        this.tension = tension;
    }

    public Wheel(double tension) {
        this.tension = tension;
    }
}
