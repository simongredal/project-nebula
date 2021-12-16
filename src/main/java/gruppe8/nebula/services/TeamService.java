// Authors Simon Gredal & Malthe Gram & Mark Friis Larsen
package gruppe8.nebula.services;

import gruppe8.nebula.entities.MembershipEntity;
import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.models.Project;
import gruppe8.nebula.models.Team;
import gruppe8.nebula.repositories.TeamRepository;
import gruppe8.nebula.requests.*;
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
        TeamEntity entity = new TeamEntity();
        entity.setName( request.name() );

        TeamEntity team = teamRepository.createTeam( entity );
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

    public List<MembershipEntity> getMembersForTeam(Account account, Long teamId) {
        Boolean allowed = membershipService.accountHasMembershipInTeam(account, teamId);
        if (!allowed) { return null; }
        return membershipService.getMembershipsForTeam(teamId, true);
    }
    public List<MembershipEntity> getInvitationsForTeam(Account account, Long teamId) {
        Boolean allowed = membershipService.accountHasMembershipInTeam(account, teamId);
        if (!allowed) { return null; }
        return membershipService.getMembershipsForTeam(teamId, false);
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
    public Boolean removeMembershipFromTeam(Account account, MembershipDeletionRequest request) {
        Boolean allowed = membershipService.accountHasMembershipInTeam(account, request.teamId());
        Boolean possible = membershipService.membershipIsPartOfTeam(request.membershipId(), request.teamId());

        if (!allowed || !possible) { return false; }

        return membershipService.deleteMembership(request.membershipId());
    }

    public Team getTeam(Account account, Long teamId) {
        Boolean allowed = membershipService.accountHasMembershipInTeam(account, teamId);

        if (!allowed) { return null; }

        TeamEntity teamEntity = teamRepository.getTeamById(teamId);
        List<Project> projects = projectService.getProjectsByTeamId(teamId);

        Team team = new Team(teamEntity);
        team.getProjects().addAll(projects);

        return team;
    }

    public Boolean createInvitation(Account account, InvitationCreationRequest request) {
        Boolean allowed = membershipService.accountHasMembershipInTeam(account, request.teamId());
        if (!allowed) { return false; }

        Account invitee = accountService.getAccountByEmail(request.invitation());
        if (invitee == null) { return false; }

        return membershipService.sendInvitation(request.teamId(), invitee.id());
    }
}
