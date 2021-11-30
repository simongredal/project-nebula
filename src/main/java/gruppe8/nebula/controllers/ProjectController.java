package gruppe8.nebula.controllers;

import gruppe8.nebula.models.Account;
import gruppe8.nebula.models.Project;
import gruppe8.nebula.services.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final Logger log;
    private final ProjectService projectService;

    private ProjectController(ProjectService projectService) {
        this.projectService = projectService;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping
    public String index(Authentication authentication, Model model) {
        Account account = (Account) authentication.getPrincipal();

        model.addAttribute("account", account);

        log.info("GET /projects: Model="+model);

        return "projects";
    }

    @GetMapping("/tasks")
    public String task(Authentication authentication, Model model) {
        Account account = (Account) authentication.getPrincipal();

        model.addAttribute("account", account);
        model.addAttribute("project", projectService.getProjectById(1L));

        log.info("GET /projects: Model="+model);

        return "task";
    }
}
