package gruppe8.nebula.controllers;

import gruppe8.nebula.requests.TeamCreationRequest;
import gruppe8.nebula.services.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TeamController {
    private final TeamService teamService;
    private final Logger log;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping("/teams")
    public String teams() {
        return "teams";
    }

    @PostMapping("/teams/create")
    public String addTeam(TeamCreationRequest request) {
        log.info("POST /teams/create: TeamCreationRequest=" + request.toString());

        Boolean success = teamService.addTeam(request);
        if (success) {
            log.info("Successful team add");
            return "redirect:/teams";
        }

        log.info("Unsuccessful team add");
        return "redirect:/teams";
    }
}