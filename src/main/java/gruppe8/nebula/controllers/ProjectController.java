// Authors Mark Friis Larsen & Frederik Vilhelmsen
package gruppe8.nebula.controllers;

import gruppe8.nebula.models.Account;
import gruppe8.nebula.models.Message;
import gruppe8.nebula.models.Project;
import gruppe8.nebula.models.Team;
import gruppe8.nebula.requests.*;
import gruppe8.nebula.services.ProjectService;
import gruppe8.nebula.services.ResourceService;
import gruppe8.nebula.services.TaskService;
import gruppe8.nebula.services.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

/*
This class handles both Projects and Tasks.
 */

@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final Logger log;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final TeamService teamService;
    private final ResourceService resourceService;

    private ProjectController(ProjectService projectService, TaskService taskService, TeamService teamService, ResourceService resourceService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.teamService = teamService;
        this.resourceService = resourceService;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping
    public String index(Authentication authentication, Model model) {
        // Instantiates an account object which get authenticated.
        Account account = (Account) authentication.getPrincipal();
        List<Team> allTeams = teamService.getTeamsForAccount(account).stream()
                .map(membershipEntity -> teamService.getTeam(account, membershipEntity.teamId()))
                .toList();

        model.addAttribute("allTeams", allTeams);

        log.info("GET /projects: Model="+model);

        return "projects";
    }

    @GetMapping("/{projectId}")
    public String task(@PathVariable Long projectId, Authentication authentication, Model model) {
        log.info("GET /projects/"+projectId);

        Account account = (Account) authentication.getPrincipal();
        Project project = projectService.getProjectById(account, projectId);

        if (project == null) {
            Message message = new Message(Message.Type.WARNING, "Something went wrong and we couldn't fetch the project. Maybe you aren't a part of the team yet?");
            model.addAttribute("message", message);
        }

        model.addAttribute("project", project);
        return "project";
    }

    // TODO: Maybe move task related stuff to a task controller?
    @PostMapping("/create")
    public RedirectView addTask(TaskCreationRequest request, Authentication authentication, RedirectAttributes redirectAttributes) {
        Account account = (Account) authentication.getPrincipal();

        log.info("POST /projects/create: TaskCreationRequest=%s, Account=%s".formatted(request, account));
        Boolean success = taskService.createTask(request);

        Message message;
        if (success) { message = new Message(Message.Type.SUCCESS, "Task created successfully."); }
        else { message = new Message(Message.Type.WARNING, "Task could not be created successfully."); }
        redirectAttributes.addFlashAttribute("message", message);

        return new RedirectView("/projects/"+request.projectId());
    }
    @PostMapping("/delete")
    public String removeTask(TaskDeletionRequest request, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();

        log.info("POST /projects/delete: TaskDeletionRequest=%s, Account=%s".formatted(request, account));

        // TODO: Check authorization
        Boolean success = taskService.deleteTask(request);

        if (success) {
            log.info("Successful Task deletion");
            return "redirect:/projects/"+request.projectId();
        }

        log.info("Unsuccessful Task deletion");
            return "redirect:/projects/"+request.projectId();
    }

    @PostMapping("/update")
    public String editTask(TaskUpdateRequest request, Authentication authentication){
        Account account = (Account) authentication.getPrincipal();

        log.info("POST /projects/update: TaskUpdateRequest=%s, Account=%s".formatted(request,account));

        // TODO: Check authorization
        Boolean success = taskService.updateTask(request);

        if (success){
            log.info("Successful task edit");
            return "redirect:/edit-task/" + request.projectId();
        }
        log.info("Unsuccessful Task update");
            return "redirect:/projects/"+request.projectId();
    }

    @PostMapping("/new-resource")
    public String createResource(ResourceCreationRequest request, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();

        log.info("POST /teams/create: TaskCreationRequest=%s, Account=%s".formatted(request,account));

        // TODO: Check authorization
        Boolean success = resourceService.createResource(request);
        if (success) {
            log.info("Successful Resource create");
            return "redirect:/projects/"+request.project_id();
        }

        log.info("Unsuccessful Resource create");
        return "redirect:/projects/"+request.project_id();
    }

    @PostMapping("/delete-resource")
    public String deleteResource(ResourceDeletionRequest request, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();

        log.info("POST /teams/create: TaskCreationRequest=%s, Account=%s".formatted(request,account));

        // TODO: Check authorization
        Boolean success = resourceService.deleteResource(request);
        if (success) {
            log.info("Successful Resource delete");
            return "redirect:/projects/"+request.project_id();
        }

        log.info("Unsuccessful Resource delete");
        return "redirect:/projects/"+request.project_id();
    }

    @PostMapping("/new-project")
    public RedirectView newProject(CreateProjectRequest request, Authentication authentication, RedirectAttributes redirectAttributes){
        Account account = (Account) authentication.getPrincipal();
        log.info("POST /projects/new-project: CreateProjectRequest=%s, Account=%s".formatted(request, account));

        Boolean success = projectService.createProject(account, request);
        Message message;
        if (success) { message = new Message(Message.Type.SUCCESS, "New project created succesfully."); }
        else { message = new Message(Message.Type.ERROR, "Could not create new project."); }
        redirectAttributes.addFlashAttribute(message);

        return new RedirectView("/teams/" + request.teamId());
    }
}
