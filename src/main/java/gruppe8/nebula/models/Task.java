package gruppe8.nebula.models;

import gruppe8.nebula.entities.TaskEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.sql.Date;

public class Task {
    private Long id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final List<Task> subtasks = new ArrayList<>();

    public Task(TaskEntity taskEntity){
        this.id = taskEntity.id();
        this.name = taskEntity.name();
        this.startDate = setDate(taskEntity.startDate());
        this.endDate = setDate(taskEntity.endDate());
    }

    public Task(TaskEntity task, List<TaskEntity> tasks){
        this.id = task.id();
        this.name = task.name();
        this.startDate = setDate(task.startDate());
        this.endDate = setDate(task.endDate());
        tasks.stream()
                .filter(taskEntity -> task.id().equals(taskEntity.parent()))
                .forEach( t -> this.subtasks.add(new Task(t, tasks)) );
    }
    public LocalDateTime setDate(String date) {

        LocalDateTime dateTime= null;

        try {
            dateTime = LocalDateTime.parse(date.replace("T"," "), formatter);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return dateTime;

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public LocalDateTime getStartDate() {return startDate;}
    public LocalDateTime getEndDate() {return endDate;}

    public List<Task> getSubtasks() {
        return subtasks;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add( this ); // DOH!
        for (Task task : subtasks) { tasks.addAll(task.getAllTasks()); }
        return tasks;
    }
}
