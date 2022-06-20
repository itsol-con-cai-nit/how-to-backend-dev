package chapter_6.law_of_demeter;

public class Engine {
     private boolean isWork;

    public boolean isWork() {
        return isWork;
    }

    public void setWork(boolean work) {
        isWork = work;
    }
}
