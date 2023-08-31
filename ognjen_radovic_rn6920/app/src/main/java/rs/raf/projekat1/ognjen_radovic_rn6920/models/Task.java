package rs.raf.projekat1.ognjen_radovic_rn6920.models;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

public class Task implements Serializable {
    private String name;
    private Day day;

    private LocalTime startTime;
    private LocalTime endTime;

    private String description;
    private Priority priority;
    private int id;

    public Task(String name, Day day, LocalTime startTime, LocalTime endTime, String description, Priority priority) {
        this.name = name;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return day.getDate().equals(((Task) o).getDay().getTasks()) && this.getId()==((Task) o).getId();
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, day, startTime, endTime, description, priority);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
