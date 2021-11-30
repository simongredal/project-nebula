package gruppe8.nebula.controllers;

import gruppe8.nebula.models.Account;
import gruppe8.nebula.models.Project;
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

    private ProjectController() {
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping
    public String index(Authentication authentication, Model model) {
        Account account = (Account) authentication.getPrincipal();

        model.addAttribute("account", account);

        log.info("GET /projects: Model="+model);

        return "projects";
    }
}
