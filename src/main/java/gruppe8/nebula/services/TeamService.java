package gruppe8.nebula.services;

import gruppe8.nebula.entities.TaskEntity;
import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.models.Team;

import gruppe8.nebula.repositories.TeamRepository;
import gruppe8.nebula.requests.TeamRequest;
import gruppe8.nebula.requests.TeamDeletionRequest;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final Argon2PasswordEncoder passwordEncoder;

    public TeamService(TeamRepository teamRepository, Argon2PasswordEncoder passwordEncoder){
        this.teamRepository = teamRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Boolean addTeam(TeamRequest request) {
        Team team = new Team(
                request.name()
        );

        return teamRepository.createTeam(team);
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

        return teamRepository.updateTeam(teamOld,teamNew);
    }
    public List<TeamEntity> getAllTeams() {
        return teamRepository.getAllTeams();
    }

}
