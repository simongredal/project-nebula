package gruppe8.nebula.services;

import gruppe8.nebula.controllers.TaskController;
import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.models.Project;
import gruppe8.nebula.models.Task;
import gruppe8.nebula.models.Team;
import gruppe8.nebula.repositories.ProjectRepository;
import gruppe8.nebula.repositories.TaskRepository;
import gruppe8.nebula.entities.TaskEntity;
import gruppe8.nebula.repositories.TeamRepository;
import gruppe8.nebula.requests.TaskCreationRequest;
import gruppe8.nebula.requests.TaskDeletionRequest;
import gruppe8.nebula.requests.TeamCreationRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectService projectService;


    public TaskService(TaskRepository taskRepository, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;

    }

    /*
    public List<Task> getTasksFromProject(Long projectId) {
        Project project = projectService.getProjectById(projectId);

        return taskRepository.getTasksForProject();
    }*/

    public Boolean createTask(TaskCreationRequest request) {
        Task task = new Task(new TaskEntity(request.id(),request.projectId(), request.parentId(), request.name()));
        return taskRepository.createTask(task, request.parentId(), request.projectId());

    }

    public Boolean deleteTask(TaskDeletionRequest request) {
        return taskRepository.deleteTask(request.id());

    }


}
