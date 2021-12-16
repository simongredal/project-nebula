// Authors Simon Gredal & Malthe Gram & Mark Friis Larsen
package gruppe8.nebula.models;

import gruppe8.nebula.entities.TaskEntity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Task {
    private Long id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long duration;
    private Long resourceId;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final List<Task> subtasks = new ArrayList<>();
    private final Integer estimatedCost;

    public Task(TaskEntity taskEntity){
        this.id = taskEntity.id();
        this.name = taskEntity.name();
        this.startDate = taskEntity.startDate();
        this.endDate = taskEntity.endDate();
        this.duration = taskEntity.duration();
        this.resourceId = taskEntity.resourceId();
        this.estimatedCost = taskEntity.estimatedCost();
    }

    public Task(TaskEntity task, List<TaskEntity> tasks){
        this.id = task.id();
        this.name = task.name();
        this.startDate = task.startDate();
        this.endDate = task.endDate();
        this.duration = task.duration();
        this.resourceId = task.resourceId();
        this.estimatedCost = task.estimatedCost();
        tasks.stream()
                .filter(taskEntity -> task.id().equals(taskEntity.parent()))
                .forEach( t -> this.subtasks.add(new Task(t, tasks)) );
    }

    public Long getId() {
        return id;
    }
    public Long getDuration() {return duration;}
    public Long getResource() {return resourceId;}
    public String getName() {return name;}
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

    public String getDates() {
        String startDate = getStartDate().toString().replace("T"," ")+":00.0";
        String endDate = getEndDate().toString().replace("T"," ")+":00.0";
        String dates= startDate + "/" + endDate;
        return dates;
    }

    // TODO: Add minutes and days
    public String getRemainingTimeForTask(){

        LocalDateTime startDate = getStartDate();
        LocalDateTime endDate = getEndDate();

        Duration duration = Duration.between(startDate, endDate);


        return duration.toHours() + " hours";

    }

    public int getTotalTaskSpanDays(){
        if (startDate==null || endDate==null) {
            return -1;
        }
        return (int) ChronoUnit.DAYS.between(startDate, endDate)+1;
    }

    public double getHoursPerDay(){
        double days = getTotalTaskSpanDays();
        double hours = duration;
        return hours / days;
    }

    public Integer getEstimatedCost() {
        return estimatedCost;
    }

}
