package gruppe8.nebula.models;

import gruppe8.nebula.entities.ProjectEntity;
import gruppe8.nebula.entities.TaskEntity;

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

