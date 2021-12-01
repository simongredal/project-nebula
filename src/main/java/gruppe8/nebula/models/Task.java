package gruppe8.nebula.models;

import gruppe8.nebula.entities.TaskEntity;

import java.util.*;
import java.sql.Date;

public class Task {
    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private final List<Task> subtasks = new ArrayList<>();

    public Task(TaskEntity taskEntity) {
        this.id = taskEntity.id();
        this.name = taskEntity.name();
        this.startDate = taskEntity.startDate();
        this.endDate = taskEntity.endDate();
    }

    public Task(TaskEntity task, List<TaskEntity> tasks) {
        this.id = task.id();
        this.name = task.name();
        this.startDate = task.startDate();
        this.endDate = task.endDate();
        tasks.stream()
                .filter(taskEntity -> task.id().equals(taskEntity.parent()))
                .forEach( t -> this.subtasks.add(new Task(t, tasks)) );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public Date getStartDate() {return startDate;}
    public Date getEndDate() {return endDate;}

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
