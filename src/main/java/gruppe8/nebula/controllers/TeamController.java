package gruppe8.nebula.controllers;

import gruppe8.nebula.entities.MembershipEntity;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.models.Message;
import gruppe8.nebula.models.Team;
import gruppe8.nebula.requests.*;
import gruppe8.nebula.services.AccountService;
import gruppe8.nebula.services.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
        List<MembershipEntity> invites = teamService.getInvitationsForAccount(account);
        List<Account> allAccounts = accountService.loadAllAccounts();

        model.addAttribute("teams", teams);
        model.addAttribute("invites", invites);
        model.addAttribute("allAccounts", allAccounts);

        log.info("GET /teams: Model=%s".formatted(model));
        return "teams";
    }

    @GetMapping("{teamId}")
    public String teams(@PathVariable Long teamId, Authentication authentication, Model model) {
        log.info("GET /teams/" + teamId);

        Account account = (Account) authentication.getPrincipal();
        Team team = teamService.getTeam(account, teamId);
        List<MembershipEntity> members = teamService.getMembersForTeam(account, teamId);
        List<MembershipEntity> invites = teamService.getInvitationsForTeam(account, teamId);
        List<Account> allAccounts = accountService.loadAllAccounts();

        model.addAttribute("team", team);
        model.addAttribute("members", members);
        model.addAttribute("invites", invites);
        model.addAttribute("allAccounts", allAccounts);

        log.info("Model=" + model);
        return "team";
    }

    // Creates a new Invitation for a Team
    @PostMapping("invite")
    public String invite(InvitationCreationRequest request, Authentication authentication, Model model) {
        log.info("POST /teams/invite");

        Account account = (Account) authentication.getPrincipal();
        Boolean success = teamService.createInvitation(account, request);

        if (success) {
            log.info("Successful invite");
            return "redirect:/teams"+request.teamId();
        }
        log.info("unsuccessful invite");
        return "redirect:/teams";

    }

    // Remove a Membership from a Team whether it was accepted or not
    @PostMapping("uninvite")
    public String invite(MembershipDeletionRequest request, Authentication authentication, Model model) {
        log.info("POST /teams/uninvite");

        Account account = (Account) authentication.getPrincipal();
        Boolean success = teamService.removeMembershipFromTeam(account, request);
        if (success){
            log.info("Successful uninvite");
            return "redirect:/teams"+request.teamId();
        }
        log.info("unsuccessful uninvite");
        return "redirect:/teams";
    }

    @PostMapping("create")
    public RedirectView addTeam(TeamCreationRequest request, Authentication authentication, RedirectAttributes redirectAttributes) {
        Account account = (Account) authentication.getPrincipal();
        log.info("POST /teams/create: TeamCreationRequest=%s, Account=%s".formatted(request, account));

        Boolean success = teamService.createTeam(account, request);

        Message message;
        if (success) { message = new Message(Message.Type.SUCCESS, "Team has been created."); }
        else { message = new Message(Message.Type.WARNING, "Team could not be created."); }
        redirectAttributes.addFlashAttribute("message", message);

        return new RedirectView("/teams");
    }

    @PostMapping("delete")
    public RedirectView deleteTeam(Authentication authentication, TeamDeletionRequest request, RedirectAttributes redirectAttributes) {
        Account account = (Account) authentication.getPrincipal();

        log.info("POST /teams/delete: TeamDeletionRequest=%s, Account=%s".formatted(request, account));
        Boolean success = teamService.deleteTeam(account, request);

        Message message;
        if (success) { message = new Message(Message.Type.INFO, "Team has been deleted."); }
        else { message = new Message(Message.Type.WARNING, "Team could not be deleted."); }
        redirectAttributes.addFlashAttribute("message", message);

        return new RedirectView("/teams");
    }

    @PostMapping("accept")
    public String acceptMembership(MembershipUpdateRequest request, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        log.info("POST /teams/accept request=" + request);

        // TODO: Check authorization?
        Boolean success = teamService.acceptMembership(account, request);
        if (success) {
            log.info("Successful accept");
            return "redirect:/teams";
        }

        log.info("Unsuccessful accept");
        return "redirect:/teams";
    }


    @PostMapping("reject")
    public String rejectMembership(MembershipUpdateRequest request, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        log.info("POST /teams/reject request=" + request);

        // TODO: Check authorization?
        Boolean success = teamService.rejectMembership(account, request);
        if (success) {
            log.info("Successful reject");
            return "redirect:/teams";
        }
        log.info("Unsuccessful reject");
        return "redirect:/teams";
    }
}