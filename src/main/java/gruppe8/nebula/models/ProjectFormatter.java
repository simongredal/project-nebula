package gruppe8.nebula.models;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProjectFormatter extends Project {
    public ProjectFormatter(Long id, String name) {
        super(id, name);
    }

    public List<String> getOverview() {
        List<String> overview = new ArrayList<>();
        overview.add("OVERVIEW");

        overview.add(
                "Your project \"" + getName() + "\" spans a total of " + getTotalProjectSpanDays() +
                " days, from the dates: " + getStartDate() + "-" + getEndDate()
        );

        overview.add("MAIN TASKS");

        int totalDuration = 0;
        //parentless task list
        for (Task task : getSubtasks()) {
            int maxDuration = LevelOrderTraversal(task).stream()
                    .mapToInt(v -> v)
                    .max().orElseThrow(NoSuchElementException::new);
            totalDuration += maxDuration;

            overview.add("[%s] duration: %s hours, from %s to %s"
                    .formatted(task.getName(), maxDuration, task.getStartDate(), task.getEndDate()));

            double avgWorkHours = (double) maxDuration / (double) task.getTotalTaskSpanDays();

            overview.add(" - To meet deadline, this task requires an average of %s hours of work per day".formatted(avgWorkHours));
        }
        return overview;
    }
    //get all insights
    public List<Insight> getInsights(){
        List<Insight> insights = new ArrayList<>();

        for (Task t : getAllTasks().stream().filter(Predicate.not(t -> DurationCheck(t)==null)).collect(Collectors.toList())) {
            insights.add(DurationCheck(t));
        }
        for (Task t : getAllTasks().stream().filter(Predicate.not(t -> AverageWorkHoursCheck(t)==null)).collect(Collectors.toList())) {
            insights.add(AverageWorkHoursCheck(t));
        }

        return insights;
    }

    //build insights
    public Insight DurationCheck(Task task) {
        int childDurationSum = (int) task.getSubtasks().stream().mapToLong(Task::getDuration).sum();
        if (task.getDuration() >= childDurationSum) {
            return null;
        } else {
            Insight insight = new Insight(
                    "Duration warning",
                    "The total combined duration of the child tasks for task: [%s] exceed the parent task duration".formatted(getName())
                            + "\n - parent [%s] - duration: %s".formatted(task.getName(),task.getDuration())
                            + "\n - children's total duration: %s".formatted(childDurationSum)
            );
            return insight;
        }
    }
    //checks childless tasks for avg duration > 8 hours
    public Insight AverageWorkHoursCheck(Task task) {
        //int childDurationSum = (int) task.getSubtasks().stream().mapToLong(Task::getDuration).sum();
        if (task.getHoursPerDay() <= 8) {
            return null;
        } else {
            Insight insight = new Insight(
                    "Average Hours warning",
                    "The average amount of hours per day for childless task [%s] is above 8 hours".formatted(getName())
                            + "\n - [%s] - avg work hours: %s".formatted(task.getName(),task.getHoursPerDay())
            );
            return insight;
        }
    }
}
