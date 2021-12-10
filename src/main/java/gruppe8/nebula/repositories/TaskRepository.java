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
    private final Logger log;

    public TaskRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.log = LoggerFactory.getLogger(this.getClass());
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
                        resultSet.getTimestamp("startDate").toLocalDateTime(),
                        resultSet.getTimestamp("endDate").toLocalDateTime(),
                        resultSet.getLong("duration"),
                        resultSet.getLong("resource_id"),
                        resultSet.getInt("estimated_cost")
                ));
            }
        } catch (SQLException e) {
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error("%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
        }

        return tasks;
    }

    public boolean createTask(TaskEntity task){
        try(Connection connection = databaseManager.getConnection()){
            String query = "insert into tasks (project_id,parent_id,name,startDate,endDate,duration,resource_id,estimated_cost) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1,task.projectId());
            preparedStatement.setObject(2,task.parent()); //setting as object instead of Long, so it can also be null
            preparedStatement.setString(3,task.name());
            preparedStatement.setTimestamp(4, Timestamp.valueOf( task.startDate() ));
            preparedStatement.setTimestamp(5,Timestamp.valueOf( task.endDate() ));
            preparedStatement.setObject(6,task.duration());
            preparedStatement.setObject(7,task.resourceId());
            preparedStatement.setObject(8,task.estimatedCost());
            preparedStatement.execute();

            return preparedStatement.getUpdateCount() == 1;

        } catch (SQLException e){
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error("%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
        }
        return false;
    }
    public boolean editTask(Task task){
        try(Connection connection = databaseManager.getConnection()){
            String query = "UPDATE tasks (name,startDate,endDate,duration,resource_id,estimated_cost) VALUES (?,?,?,?,?,?) "+"WHERE id=(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,task.getName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf( task.getStartDate() ));
            preparedStatement.setTimestamp(3,Timestamp.valueOf( task.getEndDate() ));
            preparedStatement.setObject(4,task.getDuration());
            preparedStatement.setObject(5,task.getResource());
            preparedStatement.setObject(6,task.getId());
            preparedStatement.setInt(7,task.getEstimatedCost());
            preparedStatement.execute();

            return preparedStatement.getUpdateCount() == 1;

        } catch (SQLException e){
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error("%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
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
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error("%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
        }
        return false;
    }

}
