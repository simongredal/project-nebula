// Authors Simon Gredal & Malthe Gram & Mark Friis Larsen & Frederik Vilhelmsen
package gruppe8.nebula.services;

import gruppe8.nebula.entities.ProjectEntity;
import gruppe8.nebula.entities.ResourceEntity;
import gruppe8.nebula.entities.TaskEntity;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.models.Project;
import gruppe8.nebula.models.ProjectFormatter;
import gruppe8.nebula.repositories.ProjectRepository;
import gruppe8.nebula.requests.CreateProjectRequest;
import gruppe8.nebula.requests.DeleteProjectRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    private final TaskService taskService;
    private final ResourceService resourceService;
    private final ProjectRepository projectRepository;
    private final MembershipService membershipService;

    public ProjectService(ProjectRepository projectRepository, TaskService taskService, MembershipService membershipService, ResourceService resourceService) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
        this.resourceService = resourceService;
        this.membershipService = membershipService;
    }

    public Project getProjectById(Account account, Long id) {
        Boolean allowed = accountOwnsProject(account, id);
        if (!allowed) { return null; }

        ProjectEntity projectEntity = projectRepository.getProjectById(id);
        List<TaskEntity> taskEntities = taskService.getTasksFromProject(id);
        List<ResourceEntity> resourceEntities = resourceService.getResourcesFromProject(id);

        ProjectFormatter project = new ProjectFormatter(id, projectEntity.name());
        project.setProjectEntity(projectEntity);
        project.setSubtasks(taskEntities);
        project.setResources(resourceEntities);

        project.getInsights();
        return project;
    }

    public Boolean accountOwnsProject(Account account, Long projectId) {
        // Converting null to false and true to true
        Boolean accountOwnsProject = projectRepository.accountOwnsProject(account.id(), projectId);
        return (accountOwnsProject != null && accountOwnsProject);
    }

    public Boolean createProject(Account account, CreateProjectRequest request) {
        Boolean allowed = membershipService.accountHasMembershipInTeam(account, request.teamId());
        if (!allowed) { return false; }

        ProjectEntity entity = new ProjectEntity(null, request.teamId(), request.projectName());
        return projectRepository.createProject(entity);
    }

    public boolean deleteProject(Account account, DeleteProjectRequest request) {
        Boolean allowed = accountOwnsProject(account, request.id());
        if (!allowed) { return false; }
        return projectRepository.deleteProject(request.id());
    }

    public List<Project> getProjectsByTeamId(Long teamId) {
        List<ProjectEntity> projectEntities = projectRepository.getProjectsByTeamId(teamId);

        List<Project> projects = new ArrayList<>();
        for (ProjectEntity projectEntity : projectEntities) {
            Project project = new Project();
            project.setProjectEntity(projectEntity);
            project.setSubtasks(taskService.getTasksFromProject(projectEntity.id()));
            projects.add(project);
        }

        return projects;
    }
}
