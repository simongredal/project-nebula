package gruppe8.nebula.repositories;

import gruppe8.nebula.models.Task;
import gruppe8.nebula.services.DatabaseManager;
import gruppe8.nebula.entities.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
            String query = "SELECT * FROM tasks WHERE project_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, projectId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tasks.add(new TaskEntity(
                        resultSet.getLong("id"),
                        resultSet.getLong("project_id"),
                        resultSet.getLong("parent_id"),
                        resultSet.getString("name"),
                        resultSet.getTimestamp("startDate").toLocalDateTime().toString(),
                        resultSet.getTimestamp("endDate").toLocalDateTime().toString()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public boolean createTask(Task task,Long parentId,Long projectId){
        try(Connection connection = databaseManager.getConnection()){
            String query = "insert into tasks (project_id,parent_id,name,startDate,endDate) VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1,projectId);
            preparedStatement.setObject(2,parentId); //setting as object instead of Long, so it can also be null
            preparedStatement.setString(3,task.getName());
            preparedStatement.setTimestamp(4, Timestamp.valueOf( task.getStartDate() ));
            preparedStatement.setTimestamp(5,Timestamp.valueOf( task.getEndDate() ));

            preparedStatement.execute();

            return preparedStatement.getUpdateCount() == 1;

        } catch (SQLException e){
            logger.error(e.getMessage());
        }
        return false;
    }

    public boolean deleteTask(Long taskId) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "DELETE FROM tasks WHERE (id = ?);";
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, taskId);

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
