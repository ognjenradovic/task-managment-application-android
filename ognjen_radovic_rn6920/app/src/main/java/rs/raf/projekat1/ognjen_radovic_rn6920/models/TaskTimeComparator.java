package rs.raf.projekat1.ognjen_radovic_rn6920.models;

import java.util.Comparator;
import java.time.LocalTime;

public class TaskTimeComparator implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2) {
        LocalTime startTime1 = task1.getStartTime();
        LocalTime startTime2 = task2.getStartTime();

        if (startTime1.isBefore(startTime2)) {
            return -1;
        } else if (startTime1.isAfter(startTime2)) {
            return 1;
        } else {
            return 0;
        }
    }
}