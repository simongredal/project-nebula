package gruppe8.nebula.models;

import gruppe8.nebula.entities.ProjectEntity;
import gruppe8.nebula.entities.TaskEntity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
        String inputString1 = "23 01 1997";
        String inputString2 = "27 04 1997";

        try {
            long daysBetween = Duration.between(getStartDate(), getEndDate()).toDays();
            System.out.println ("Days: " + daysBetween);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public List<Date> getTotalProjectSpanDates() {
        List<Date> projectSpan = new ArrayList<>();
        projectSpan.add(getStartDate());


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

