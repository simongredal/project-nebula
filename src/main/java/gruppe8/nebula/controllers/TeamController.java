package gruppe8.nebula.controllers;

import com.google.gson.Gson;
import gruppe8.nebula.entities.MembershipEntity;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.requests.MembershipUpdateRequest;
import gruppe8.nebula.requests.TeamCreationRequest;
import gruppe8.nebula.services.AccountService;
import gruppe8.nebula.services.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;
    private final Logger log;
    private final AccountService accountService;

    public TeamController(TeamService teamService, AccountService accountService) {
        this.teamService = teamService;
        this.accountService = accountService;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping
    public String index(Authentication authentication, Model model) {
        Account account = (Account) authentication.getPrincipal();
        List<MembershipEntity> teams = teamService.getTeamsForAccount(account);
        List<MembershipEntity> invitations = teamService.getInvitationsForAccount(account);
        List<Account> allAccounts = accountService.getAllAccounts();

        model.addAttribute("account", account);
        model.addAttribute("teams", teams);
        model.addAttribute("invites", invitations);
        model.addAttribute("allAccounts",accountService.getAllAccounts());

        log.info("GET /teams: Model=%s".formatted(model));
        return "teams";
    }

    @GetMapping("/{teamId}")
    public String teams(@PathVariable Long teamId, Authentication authentication, Model model) {
        log.info("GET /teams/"+teamId);
        return "team_page";
    }

    @PostMapping("/accept")
    public String acceptMembership(MembershipUpdateRequest request, Authentication authentication) {
        log.info("POST /teams/accept request="+request);
        return "redirect:/teams";
    }

    @PostMapping("/reject")
    public String rejectMembership(MembershipUpdateRequest request, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        log.info("POST /teams/reject request="+request);

        Boolean success = teamService.rejectMembership(account, request);
        return "redirect:/teams";
    }

    @PostMapping("/create")
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