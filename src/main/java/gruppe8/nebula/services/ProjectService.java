package gruppe8.nebula.services;

import gruppe8.nebula.entities.MembershipEntity;
import gruppe8.nebula.entities.ProjectEntity;
import gruppe8.nebula.entities.ResourceEntity;
import gruppe8.nebula.entities.TaskEntity;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.models.Membership;
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

    public Project getProjectById(Long id) {
        ProjectEntity projectEntity = projectRepository.getProjectById(id);
        List<TaskEntity> taskEntities = taskService.getTasksFromProject(id);
        List<ResourceEntity> resourceEntities = resourceService.getResourcesFromProject(id);

        ProjectFormatter project = new ProjectFormatter(id,projectEntity.name());
        project.setProjectEntity(projectEntity);
        project.setSubtasks(taskEntities);
        project.setResources(resourceEntities);

        //project.getDurationLayers();
        project.getInsights();
        return project;
    }

    public Boolean createProject(Account account, CreateProjectRequest request) {
        Project project = new Project(request.teamId(), request.projectName());
        return projectRepository.createProject(project, account);
    }

    public boolean deleteProject(Account account, DeleteProjectRequest request) {
        Project project = new Project(request.id(), null);
        return projectRepository.deleteProject(project, account);
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
