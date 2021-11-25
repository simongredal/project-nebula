package gruppe8.nebula.repositories;

import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.models.Team;
import gruppe8.nebula.services.DatabaseManager;
import gruppe8.nebula.entities.ProjectEntity;
import gruppe8.nebula.entities.TeamEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TeamRepository {
    private final DatabaseManager databaseManager;
    private final Logger logger;

    public TeamRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public boolean createTeam(Team team){
        try(Connection connection = databaseManager.getConnection()){
            String query = "insert into Nebula.teams (name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,team.getName());

            preparedStatement.execute();

            return preparedStatement.getUpdateCount() == 1;

        } catch (SQLException e){
            logger.error(e.getMessage());
        }
        return false;
    }

    public boolean deleteTeam(Team team) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "DELETE FROM teams WHERE (id = ?);";
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, team.getId());

            int changedRows = preparedStatement.executeUpdate();
            if (changedRows == 1) {
                connection.commit();
                System.out.println("team deleted: " + team.getName());
                return true;
            }
            connection.rollback();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }
    public boolean updateTeam(Team teamOld, Team teamNew) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "UPDATE nebula.teams SET name = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, teamNew.getName());
            preparedStatement.setLong(2, teamOld.getId());

            int update = preparedStatement.executeUpdate();
            if (update == 1) {
                System.out.println("team name updated: " + teamNew.getName());
                return true;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }


    public List<TeamEntity> getAllTeams() {
        List<TeamEntity> teams = new ArrayList<>();
        try (Connection connection = databaseManager.getConnection()) {
            String query = "SELECT * FROM Nebula.teams";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                teams.add(new TeamEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                ));
            }
            return teams;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teams;
    }

}
