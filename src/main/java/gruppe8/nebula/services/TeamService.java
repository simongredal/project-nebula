package gruppe8.nebula.services;

import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.models.Team;
import gruppe8.nebula.repositories.TeamRepository;
import gruppe8.nebula.requests.TeamDeletionRequest;
import gruppe8.nebula.requests.TeamCreationRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public Boolean addTeam(TeamCreationRequest request) {
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

        return teamRepository.updateTeam(teamOld, teamNew);
    }
    public List<TeamEntity> getAllTeams() {
        return teamRepository.getAllTeams();
    }
}
