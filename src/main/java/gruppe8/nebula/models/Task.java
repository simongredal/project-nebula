package gruppe8.nebula.models;

import gruppe8.nebula.entities.TaskEntity;

import java.util.*;

public class Task {
    private Long id;
    private String name;
    private final List<Task> subtasks = new ArrayList<>();

    public Task(TaskEntity taskEntity) {
        this.id = taskEntity.id();
        this.name = taskEntity.name();
    }

    public Task(TaskEntity task, List<TaskEntity> tasks) {
        this.id = task.id();
        this.name = task.name();
        tasks.stream()
                .filter(taskEntity -> task.id().equals(taskEntity.parent()))
                .forEach( t -> this.subtasks.add(new Task(t, tasks)) );
    }

    @Override
    public String toString() {
        return "Task {<br>\n" +
                "id=" + id + "<br>\n" +
                "name='" + name + "'<br>\n" +
                "subtasks=" + subtasks + "<br>\n" +
                "}";
    }
}
