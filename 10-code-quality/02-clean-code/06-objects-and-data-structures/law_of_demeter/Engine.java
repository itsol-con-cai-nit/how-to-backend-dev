package _02_clean_code._06_objects_and_data_structures.law_of_demeter;

public class Engine {
    private boolean isWork;

    public boolean isWork() {
        return isWork;
    }

    public void setWork(boolean work) {
        isWork = work;
    }
}
