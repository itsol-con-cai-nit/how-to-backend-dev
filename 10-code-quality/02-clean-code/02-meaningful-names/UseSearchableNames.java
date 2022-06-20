package chapter_2;

import java.util.ArrayList;

public class UseSearchableNames {

    public void getBadExample() {
        int s = 0;
        ArrayList<Integer> t = new ArrayList<Integer>();
        t.add(34);
        for (int j = 0; j < 34; j++) {
            s += (t.get(j) * 4) / 5;
        }
    }

    public void getGoodExample() {
        int realDaysPerIdealDay = 4;
        int workDaysPerWeek = 5;
        int numberOfTasks = 34;
        ArrayList<Integer> taskEstimate = new ArrayList<Integer>();
        int sum = 0;
        for (int j = 0; j < numberOfTasks; j++) {
            int realTaskDays = taskEstimate.get(j) * realDaysPerIdealDay;
            int realTaskWeeks = (realTaskDays / workDaysPerWeek);
            sum += realTaskWeeks;
        }
    }
}
