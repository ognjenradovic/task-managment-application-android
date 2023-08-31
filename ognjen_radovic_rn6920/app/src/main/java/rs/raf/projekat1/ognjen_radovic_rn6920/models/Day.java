package rs.raf.projekat1.ognjen_radovic_rn6920.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Day implements Serializable {
    private LocalDate date;
    private List<Task> tasks;

    private int id=0;

    private boolean emptyDay=false;

    public Priority highestPriority() {
        Priority highest = Priority.LOW;
        for (Task task : tasks) {
            if (task.getPriority() == Priority.HIGH) {
                highest = Priority.HIGH;
                break;
            } else if (task.getPriority() == Priority.MEDIUM && highest != Priority.HIGH) {
                highest = Priority.MEDIUM;
            }
        }
        return highest;
    }

    public Day(LocalDate date) {
        this.date = date;
        this.tasks=new ArrayList<>();
    }

    public Day(boolean emptyDay) {
        this.emptyDay = emptyDay;
        this.tasks=new ArrayList<>();
    }

    public boolean isEmptyDay() {
        return emptyDay;
    }

    public void setEmptyDay(boolean emptyDay) {
        this.emptyDay = emptyDay;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        task.setId(this.id);
        tasks.add(task);
        this.id++;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

//    public void addTask(Task task){
//        this.tasks.add(task);
//    }
}
