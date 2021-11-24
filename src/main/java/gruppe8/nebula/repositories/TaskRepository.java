package gruppe8.nebula.repositories;

import gruppe8.nebula.DatabaseManager;
import gruppe8.nebula.entities.TaskEntity;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {
    private final DatabaseManager databaseManager;

    public TaskRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public List<TaskEntity> getTasksForProject(Long projectId) {
        List<TaskEntity> tasks = new ArrayList<>();
        try (Connection connection = databaseManager.getConnection()) {
            String query = "SELECT * FROM tasks WHERE tasks.project_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, projectId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tasks.add(new TaskEntity(
                        resultSet.getLong("id"),
                        resultSet.getLong("project_id"),
                        resultSet.getLong("parent"),
                        resultSet.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }
}
