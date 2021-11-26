package gruppe8.nebula.controllers;

import gruppe8.nebula.models.Account;
import gruppe8.nebula.requests.TeamCreationRequest;
import gruppe8.nebula.services.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class TeamController {
    private final TeamService teamService;
    private final Logger log;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping("/teams")
    public String teams(Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        log.info("GET /teams: Account=%s".formatted(account));
        return "teams";
    }

    @PostMapping("/teams/create")
    public String addTeam(TeamCreationRequest request, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        log.info("POST /teams/create: TeamCreationRequest=%s, Account=%s".formatted(request, account));

        Boolean success = teamService.createTeam(account, request);
        if (success) {
            log.info("Successful Team create");
            return "redirect:/teams";
        }

        log.info("Unsuccessful Team create");
        return "redirect:/teams";
    }
}