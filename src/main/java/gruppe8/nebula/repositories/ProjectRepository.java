package gruppe8.nebula.repositories;

import gruppe8.nebula.models.Account;
import gruppe8.nebula.models.Project;
import gruppe8.nebula.services.DatabaseManager;
import gruppe8.nebula.entities.ProjectEntity;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class ProjectRepository {
    private final DatabaseManager databaseManager;
    private final Logger log;

    public ProjectRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    public ProjectEntity getProjectById(Long id) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "SELECT * FROM projects WHERE projects.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new ProjectEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                );
            }
        } catch (SQLException e) {
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error(currentMethod + "%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
        }

        return null;
    }

    public boolean createProject(Project project, Account account) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "INSERT INTO projects (name, team_id) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setLong(2, project.getId());

            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows != 1) {
                log.error("Failed to create project");
                return false;
            }
            return true;
        } catch (SQLException e) {
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error(currentMethod + "%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
        }
        return false;
    }

    public boolean deleteProject(Project project, Account account) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "DELETE FROM projects WHERE (name = ? AND id = ?);";
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, Long.parseLong(project.getName()));
            preparedStatement.setLong(2, project.getId());

            log.info("deletion of project initialized");
            int changedRows = preparedStatement.executeUpdate();
            if (changedRows == 1) {
                connection.commit();
                return true;
            }
            connection.rollback();
        } catch (SQLException e) {
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error(currentMethod + "%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
        }
        return false;
    }

    public List<ProjectEntity> getProjectsByTeamId(Long teamId) {
        List<ProjectEntity> projectEntities = new ArrayList<>();

        try (Connection connection = databaseManager.getConnection()) {
            String query = "SELECT * FROM projects WHERE projects.team_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, teamId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                projectEntities.add(new ProjectEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                ));
            }
        } catch (SQLException e) {
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error(currentMethod + "%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
        }

        return projectEntities;
    }
}
