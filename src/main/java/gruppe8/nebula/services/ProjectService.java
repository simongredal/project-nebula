package gruppe8.nebula.services;

import gruppe8.nebula.entities.ProjectEntity;
import gruppe8.nebula.entities.TaskEntity;
import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.models.Project;
import gruppe8.nebula.repositories.ProjectRepository;
import gruppe8.nebula.requests.CreateProjectRequest;
import gruppe8.nebula.requests.DeleteProjectRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {
    private final TaskService taskService;
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
    }

    public Project getProjectById(Long id) {
        ProjectEntity projectEntity = projectRepository.getProjectById(id);
        List<TaskEntity> taskEntities = taskService.getTasksFromProject(id);

        Project project = new Project(id,projectEntity.name());
        project.setProjectEntity(projectEntity);
        project.setSubtasks(taskEntities);

        return project;
    }

    public Boolean createProject(Account account, CreateProjectRequest request) {
        Project project = new Project(request.id(), request.name());
        return projectRepository.createProject(project, account);
    }

    public boolean deleteProject(Account account, DeleteProjectRequest request) {
        Project project = new Project(request.id(), null);
        return projectRepository.deleteProject(project, account);
    }

}
