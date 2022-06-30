package _02_clean_code._06_objects_and_data_structures.law_of_demeter;

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
