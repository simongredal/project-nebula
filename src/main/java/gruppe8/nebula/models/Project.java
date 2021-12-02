package gruppe8.nebula.models;

import gruppe8.nebula.entities.ProjectEntity;
import gruppe8.nebula.entities.TaskEntity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Project {

    private Long id;
    private String name;
    private final List<Task> subtasks = new ArrayList<>();

    public Project() {
    }

    public Project(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public void setProjectEntity(ProjectEntity projectEntity) {
        id = projectEntity.id();
        name = projectEntity.name();
    }

    public void setSubtasks(List<TaskEntity> tasks) {
        tasks.stream()
                .filter(taskEntity -> taskEntity.parent() == 0L )
                .forEach( t -> this.subtasks.add(new Task(t, tasks)));
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        for (Task task : subtasks) { tasks.addAll(task.getAllTasks()); }
        return tasks;
    }

    public List<LocalDateTime> getAllDates() {
        List<LocalDateTime> dates = new ArrayList<>();
        for (Task task : getAllTasks()) {
            dates.add(task.getStartDate());
            dates.add(task.getEndDate());
        }
        return dates;
    }

    public LocalDateTime getStartDate() {
        LocalDateTime minDate = getAllDates().stream()
                .min(LocalDateTime::compareTo)
                .get();
        return minDate;
    }

    public LocalDateTime getEndDate() {
        LocalDateTime maxDate = getAllDates().stream()
                .max(LocalDateTime::compareTo)
                .get();
        return maxDate;
    }

    public int getTotalProjectSpanDays() {
        int daysBetween = -1;

        try {
            LocalDateTime date1 = getStartDate();
            LocalDateTime date2 = getEndDate();

            daysBetween = (int) ChronoUnit.DAYS.between(date1, date2); //returns days between in calendar time

        } catch (Exception e) {
            e.printStackTrace();
        }
        return daysBetween;
    }

    public List<String> getTotalProjectSpanDates() {
        List<String> dates = new ArrayList<>();

        for (int i=0; i<=getTotalProjectSpanDays();i++) {
            Date currentDate = java.sql.Timestamp.valueOf(getStartDate().plusDays(i));
            String[] dateString= currentDate.toString().split(" ",0);
            String dateFormatted = dateString[0];
            dates.add(dateFormatted);
        }
        System.out.println(dates);
        return dates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getSubtasks() {
        return subtasks;
    }

    @Override
    public String toString() {
        return "Project{"+ name +"} : ["+subtasks.toString()+"]";
    }
}

