package gruppe8.nebula.repositories;

import gruppe8.nebula.models.Task;
import gruppe8.nebula.models.Project;
import gruppe8.nebula.models.Team;
import gruppe8.nebula.services.DatabaseManager;
import gruppe8.nebula.entities.TaskEntity;
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
public class TaskRepository {
    private final DatabaseManager databaseManager;
    private final Logger logger;

    public TaskRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public List<TaskEntity> getTasksForProject(Long projectId) {
        List<TaskEntity> tasks = new ArrayList<>();
        try (Connection connection = databaseManager.getConnection()) {
            String query = "SELECT * FROM Nebula.tasks WHERE tasks.project_id = ?";
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

    public boolean createTask(Task task,Task parent,Project project){
        try(Connection connection = databaseManager.getConnection()){
            String query = "insert into Nebula.tasks (project_id,parent_id,name) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1,project.getId());
            preparedStatement.setLong(2,parent.getId());
            preparedStatement.setString(2,task.getName());

            preparedStatement.execute();

            return preparedStatement.getUpdateCount() == 1;

        } catch (SQLException e){
            logger.error(e.getMessage());
        }
        return false;
    }
    public boolean deleteTask(Task task) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "DELETE FROM tasks WHERE (id = ?);";
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, task.getId());

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
