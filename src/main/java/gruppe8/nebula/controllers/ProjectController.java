package gruppe8.nebula.controllers;

import gruppe8.nebula.models.Account;
import gruppe8.nebula.models.Project;
import gruppe8.nebula.requests.TaskCreationRequest;
import gruppe8.nebula.requests.TaskDeletionRequest;
import gruppe8.nebula.requests.TeamCreationRequest;
import gruppe8.nebula.services.ProjectService;
import gruppe8.nebula.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final Logger log;
    private final ProjectService projectService;
    private final TaskService taskService;

    private ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping
    public String index(Authentication authentication, Model model) {
        Account account = (Account) authentication.getPrincipal();

        model.addAttribute("account", account);

        log.info("GET /projects: Model="+model);

        return "projects";
    }

    @GetMapping("/{projectId}")
    public String task(@PathVariable Long projectId, Authentication authentication, Model model) {
        Account account = (Account) authentication.getPrincipal();
        model.addAttribute("project", projectService.getProjectById(projectId));

        log.info("GET /teams/"+projectId);

        return "task";
    }

    @PostMapping("/create")
    public String addTask(TaskCreationRequest request, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();

        log.info("POST /teams/create: TaskCreationRequest=%s, Account=%s".formatted(request, account));

        Boolean success = taskService.createTask(request);
        if (success) {
            log.info("Successful Task create");
            return "redirect:/projects/"+request.projectId();
        }

        log.info("Unsuccessful Task create");
        return "redirect:/projects/"+request.projectId();
    }
    @PostMapping("/delete")
    public String removeTask(TaskDeletionRequest request, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();

        log.info("POST /teams/create: TaskCreationRequest=%s, Account=%s".formatted(request, account));

        Boolean success = taskService.deleteTask(request);

        if (success) {
            log.info("Successful Task deletion");
            return "redirect:/projects/"+request.projectId();
        }

        log.info("Unsuccessful Task deletion");
        return "redirect:/projects/"+request.projectId();
    }
}
