package gruppe8.nebula.services;

import gruppe8.nebula.entities.MembershipEntity;
import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.models.Account;
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

    public TeamService(TeamRepository teamRepository, MembershipService membershipService, AccountService accountService) {
        this.teamRepository = teamRepository;
        this.membershipService = membershipService;
        this.accountService = accountService;
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
    public Boolean deleteTeam(TeamDeletionRequest request) {
        Team team = new Team(
                request.id(),
                request.name()
        );

        return teamRepository.deleteTeam(team);
    }
    public Boolean updateTeam(TeamDeletionRequest request, Team teamNew) {
        Team teamOld = new Team(
                request.id(),
                request.name()
        );

        return teamRepository.updateTeam(teamOld, teamNew);
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

    public Boolean rejectMembership(Account account, MembershipUpdateRequest request) {
        Boolean allowed = membershipService.accountOwnsMembership(account, request);

        if (!allowed) { return false; }

        return membershipService.rejectInvitation(request);
    }
}
