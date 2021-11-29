package gruppe8.nebula.controllers;

import gruppe8.nebula.models.Account;
import gruppe8.nebula.repositories.ProjectRepository;
import gruppe8.nebula.services.AccountService;
import gruppe8.nebula.services.ProjectService;
import gruppe8.nebula.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class TaskController {
    private final AccountService accountService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final Logger log;


    public TaskController(AccountService accountService, TaskService taskService, ProjectService projectService){
        this.accountService = accountService;
        this.projectService = projectService;
        this.taskService = taskService;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping("/task")
    public String task(Authentication authentication, Model model) {
        Account account = (Account) authentication.getPrincipal();
        model.addAttribute("accountList",accountService.getAllAccounts());
        log.info("GET /teams: Account=%s".formatted(account));
        System.out.println(projectService.getProjectById(1L));
        return "task";
    }
}
