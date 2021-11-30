package gruppe8.nebula.repositories;

import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.models.Team;
import gruppe8.nebula.services.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TeamRepository {
    private final DatabaseManager databaseManager;
    private final Logger log;

    public TeamRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    public boolean createTeam(Team team){
        try(Connection connection = databaseManager.getConnection()){
            String query = "INSERT INTO teams (name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,team.getName());

            preparedStatement.execute();

            return preparedStatement.getUpdateCount() == 1;

        } catch (SQLException e){
            log.error(e.getMessage());
        }
        return false;
    }

    public TeamEntity getTeamById(Long id) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "SELECT * FROM teams WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new TeamEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                );
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    public boolean deleteTeam(Long teamId) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "DELETE FROM teams WHERE (id = ?);";
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, teamId);

            int changedRows = preparedStatement.executeUpdate();
            if (changedRows == 1) {
                connection.commit();
                System.out.println("team deleted: " + teamId);
                return true;
            }
            connection.rollback();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return false;
    }
    public boolean updateTeam(Team teamOld, Team teamNew) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "UPDATE teams SET name = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, teamNew.getName());
            preparedStatement.setLong(2, teamOld.getId());

            int update = preparedStatement.executeUpdate();
            if (update == 1) {
                System.out.println("team name updated: " + teamNew.getName());
                return true;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return false;
    }


    public List<TeamEntity> getAllTeams() {
        List<TeamEntity> teams = new ArrayList<>();
        try (Connection connection = databaseManager.getConnection()) {
            String query = "SELECT * FROM teams";
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
            log.error(e.getMessage());
        }

        return teams;
    }

    public TeamEntity createTeamWithName(String name) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "INSERT INTO teams (name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (preparedStatement.getUpdateCount() == 1 && resultSet.next()) {
                return getTeamById(resultSet.getLong(1));
            }
        } catch (SQLException e){
            log.error(e.getMessage());
        }

        return null;
    }
}
