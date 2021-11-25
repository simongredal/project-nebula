package gruppe8.nebula.repositories;

import gruppe8.nebula.models.Account;
import gruppe8.nebula.models.Project;
import gruppe8.nebula.services.DatabaseManager;
import gruppe8.nebula.entities.ProjectEntity;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class ProjectRepository {
    private final DatabaseManager databaseManager;
    private final Logger logger;

    public ProjectRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public ProjectEntity getProjectById(Long id) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "SELECT * FROM Nebula.projects WHERE projects.id = ?";
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
            e.printStackTrace();
        }

        return null;
    }

    public boolean createProject(Project project, Account account) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "INSERT INTO projects (name, id) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, Project.getName());
            preparedStatement.setLong(2, Project.getId());

            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows != 1) {
                logger.error("Failed to create project");
                return false;
            }
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public boolean deleteProject(Project project, Account account) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "DELETE FROM wishlists WHERE (name = ? AND id = ?);";
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, Long.parseLong(Project.getName()));
            preparedStatement.setLong(2, Project.getId());

            System.out.println("deletion of project initialized");
            int changedRows = preparedStatement.executeUpdate();
            if (changedRows == 1) {
                connection.commit();
                return true;
            }
            connection.rollback();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

}
