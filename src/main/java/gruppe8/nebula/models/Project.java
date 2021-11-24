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

    public void setProjectEntity(ProjectEntity projectEntity) {
        this.id = projectEntity.id();
        this.name = projectEntity.name();
    }

    public void setSubtasks(List<TaskEntity> tasks) {
        tasks.stream()
                .filter(taskEntity -> taskEntity.parent() == 0L )
                .forEach( t -> this.subtasks.add(new Task(t, tasks)));

    }

    @Override
    public String toString() {
        return "Project {<br>\n" +
                "id=" + id + "<br>\n" +
                "name='" + name + "'<br>\n" +
                "subtasks=" + subtasks + "<br>\n" +
                "}";
    }
}
