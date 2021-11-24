package gruppe8.nebula.repositories;

import gruppe8.nebula.services.DatabaseManager;
import gruppe8.nebula.entities.ProjectEntity;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProjectRepository {
    private final DatabaseManager databaseManager;

    public ProjectRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
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
}
