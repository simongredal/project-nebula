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
    // TODO: FIX ME
    /*public Project getProjectById(Long id) {
        ProjectEntity projectEntity = projectRepository.getProjectById(id);
        List<TaskEntity> taskEntities = taskService.getTasksForUser(id);

        Project project = new Project();
        project.setProjectEntity(projectEntity);
        project.setSubtasks(taskEntities);

        return project;
    }

     */
    // TODO: FIX ME
    public Boolean createProject(Account account, Project project) {

        return false;
    }

    /*public boolean deleteProject(Account account, DeleteProjectRequest request) {
        Project project = new Project();
                request.ProjectId();

        return projectRepository.deleteProject(project, account);
    }

     */
}
