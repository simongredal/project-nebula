package gruppe8.nebula.models;

import gruppe8.nebula.entities.ProjectEntity;
import gruppe8.nebula.entities.ResourceEntity;
import gruppe8.nebula.entities.TaskEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Project {
    private Long id;
    private String name;
    private final List<Task> subtasks = new ArrayList<>();
    private final HashMap<Long,Resource> resources = new HashMap<>();

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

    /* below is a task tree algorithm that finds the total duration of each layer, adds it together and returns
    layer duration in a list*/
    /* for more info see: https://www.geeksforgeeks.org/generic-tree-level-order-traversal/*/
    // Prints the n-ary tree level wise
    public List<Integer> LevelOrderTraversal(Task root) {
        List<Integer> durationLayerList = new ArrayList<Integer>();
        if (root == null) {
            return durationLayerList;
        }

        System.out.println("root task:"+root.getName());
        // Standard level order traversal code
        // using queue
        Queue<Task> q = new LinkedList<>(); // Create a queue
        q.add(root); // Enqueue root
        while (!q.isEmpty())
        {
            int n = q.size();
            long durationTotal = 0;
            // If this node has children
            while (n > 0)
            {

                // Dequeue an item from queue
                // and print it
                Task p = q.peek();
                q.remove();
                durationTotal += p.getDuration();
                System.out.print(p.getDuration() + " ");
                // Enqueue all children of
                // the dequeued item
                for (int i = 0; i < p.getSubtasks().size(); i++)
                    q.add(p.getSubtasks().get(i));
                n--;
            }
            durationLayerList.add((int) durationTotal);

            // Print new line between two levels
            System.out.println();
        }
        return durationLayerList;
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

