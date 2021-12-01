package gruppe8.nebula.services;

import gruppe8.nebula.entities.MembershipEntity;
import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.models.Project;
import gruppe8.nebula.models.Team;
import gruppe8.nebula.repositories.TeamRepository;
import gruppe8.nebula.requests.MembershipUpdateRequest;
import gruppe8.nebula.requests.TeamDeletionRequest;
import gruppe8.nebula.requests.TeamCreationRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final MembershipService membershipService;
    private final AccountService accountService;
    private final ProjectService projectService;

    public TeamService(TeamRepository teamRepository, MembershipService membershipService, AccountService accountService, ProjectService projectService) {
        this.teamRepository = teamRepository;
        this.membershipService = membershipService;
        this.accountService = accountService;
        this.projectService = projectService;
    }

    public Boolean createTeam(Account currentAccount, TeamCreationRequest request) {
        TeamEntity team = teamRepository.createTeamWithName(request.name());
        if (team == null) { return false; }

        membershipService.addMembership(team, currentAccount);
        for (String email : request.invitations()) {
            Account account = accountService.getAccountByEmail(email);
            if (account != null) {
                membershipService.sendInvitation(team, account);
            }
        }

        return true;
    }
    public Boolean deleteTeam(Account account, TeamDeletionRequest request) {
        Boolean allowed = membershipService.accountHasMembershipInTeam(account, request.id());

        if (!allowed) { return false; }

        return teamRepository.deleteTeam(request.id());
    }
    public Boolean updateTeam(TeamDeletionRequest request, Team teamNew) {
        assert false : "updateTeam does not work anymore";
//        Team teamOld = new Team(
//                request.id(),
//                request.name()
//        );

        return teamRepository.updateTeam(null, teamNew);
    }
    public List<TeamEntity> getAllTeams() {
        return teamRepository.getAllTeams();
    }
    // Accepted invites
    public List<MembershipEntity> getTeamsForAccount(Account account) {
        return membershipService.getMembershipsForAccount(account, true);
    }
    // Not accepted invites
    public List<MembershipEntity> getInvitationsForAccount(Account account) {
        return membershipService.getMembershipsForAccount(account, false);
    }

    public Boolean acceptMembership(Account account, MembershipUpdateRequest request) {
        Boolean allowed = membershipService.accountOwnsMembership(account, request);

        if (!allowed) { return false; }

        return membershipService.acceptMembership(request);
    }

    public Boolean rejectMembership(Account account, MembershipUpdateRequest request) {
        Boolean allowed = membershipService.accountOwnsMembership(account, request);

        if (!allowed) { return false; }

        return membershipService.rejectMembership(request);
    }

    public Team getTeam(Account account, Long teamId) {
        Boolean allowed = membershipService.accountHasMembershipInTeam(account, teamId);

        if (!allowed) { return null; };

        TeamEntity teamEntity = teamRepository.getTeamById(teamId);
        List<Project> projects = projectService.getProjectsByTeamId(teamId);

        Team team = new Team(teamEntity);
        team.getProjects().addAll(projects);

        return team;
    }
}
