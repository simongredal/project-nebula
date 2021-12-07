package gruppe8.nebula.models;

import gruppe8.nebula.entities.ProjectEntity;
import gruppe8.nebula.entities.ResourceEntity;
import gruppe8.nebula.entities.TaskEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Project {
    private Long id;
    private String name;
    private final List<Task> subtasks = new ArrayList<>();
    private HashMap<Long,Resource> resources = new HashMap<>();
    //private final List<Resource> resources = new ArrayList<>();
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

    public void setResources(List<ResourceEntity> resourceList) {
        for (ResourceEntity r : resourceList) {
            Resource resource = new Resource(r);
            this.resources.put(resource.getId(),resource);
        }
    }

    /*
    public List<Resource> getResources() {
        return resources;
    }*/
    public HashMap<Long,Resource> getResources() {
        return resources;
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
        return getAllDates().stream()
                .min(LocalDateTime::compareTo)
                .orElse(null);
    }

    public LocalDateTime getEndDate() {
        return getAllDates().stream()
                .max(LocalDateTime::compareTo)
                .orElse(null);
    }

    public int getTotalProjectSpanDays() {
        LocalDateTime date1 = getStartDate();
        LocalDateTime date2 = getEndDate();
        if (date1==null || date2==null) {
            return -1;
        }
        return (int) ChronoUnit.DAYS.between(date1, date2);
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
        return "Project{"+ name +"} : ["+subtasks+"]";
    }
}

