package gruppe8.nebula.services;

import gruppe8.nebula.controllers.TaskController;
import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.models.Project;
import gruppe8.nebula.models.Task;
import gruppe8.nebula.models.Team;
import gruppe8.nebula.repositories.TaskRepository;
import gruppe8.nebula.entities.TaskEntity;
import gruppe8.nebula.repositories.TeamRepository;
import gruppe8.nebula.requests.TaskCreationRequest;
import gruppe8.nebula.requests.TeamCreationRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;
    private final MembershipService membershipService;
    private final AccountService accountService;

    public TaskService(TaskRepository taskRepository,
                       TeamRepository teamRepository,
                       MembershipService membershipService,
                       AccountService accountService) {
        this.taskRepository = taskRepository;
        this.teamRepository = teamRepository;
        this.membershipService = membershipService;
        this.accountService = accountService;
    }

    public List<TaskEntity> getTasksForUser(Long project) {
        return taskRepository.getTasksForProject(project);
    }


    // TODO:: FIXME FREDAG
    /*public Boolean createTask(TaskCreationRequest request, Project project) {
        TaskEntity task = taskRepository.createTask(request.name(),project.getName(),project.getId());

    }

     */

}
