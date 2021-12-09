package gruppe8.nebula.services;

import gruppe8.nebula.models.Task;
import gruppe8.nebula.repositories.TaskRepository;
import gruppe8.nebula.entities.TaskEntity;
import gruppe8.nebula.requests.TaskCreationRequest;
import gruppe8.nebula.requests.TaskDeletionRequest;
import gruppe8.nebula.requests.TaskUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskEntity> getTasksFromProject(Long projectId) {
        return taskRepository.getTasksForProject(projectId);
    }

    public Boolean createTask(TaskCreationRequest request) {
        TaskEntity task = new TaskEntity(request.id(),
                                            request.projectId(),
                                            request.parentId(),
                                            request.name(),
                                            request.startDate(),
                                            request.endDate(),
                                            request.duration(),
                                            request.resourceId(),
                                            request.estimatedCost());

        // If enddate is less than startdate the task is invalid,
        if (task.startDate().compareTo(task.endDate()) < 0) {
            return false;
        }

        return taskRepository.createTask(task);
    }

    public Boolean updateTask(TaskUpdateRequest request) {
        Task task = new Task (new TaskEntity(request.id(),
                request.projectId(),
                request.parentId(),
                request.name(),
                request.startDate(),
                request.endDate(),
                request.duration(),
                request.resourceId(),
                request.estimatedCost()));

        return taskRepository.editTask(task);
    }

    public Boolean deleteTask(TaskDeletionRequest request) {
        return taskRepository.deleteTask(request.taskId());
    }

}
