package gruppe8.nebula.repositories;

import gruppe8.nebula.entities.ResourceEntity;
import gruppe8.nebula.entities.TaskEntity;
import gruppe8.nebula.services.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ResourceRepository {
    private final DatabaseManager databaseManager;
    private final Logger logger;

    public ResourceRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public boolean createResource(ResourceEntity resource){
        try(Connection connection = databaseManager.getConnection()){
            String query = "insert into resources (id,team_id,name,color) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1,resource.id());
            preparedStatement.setLong(2,resource.team_id());
            preparedStatement.setString(3,resource.name());
            preparedStatement.setString(4,resource.color());

            preparedStatement.execute();

            return preparedStatement.getUpdateCount() == 1;

        } catch (SQLException e){
            logger.error(e.getMessage());
        }
        return false;
    }

    public boolean editResource(ResourceEntity resource){
        try(Connection connection = databaseManager.getConnection()){
            String query = "UPDATE resources (name,color) VALUES (?,?) "+"WHERE id=(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,resource.name());
            preparedStatement.setString(2,resource.color());

            preparedStatement.execute();

            return preparedStatement.getUpdateCount() == 1;

        } catch (SQLException e){
            logger.error(e.getMessage());
        }
        return false;
    }

    public boolean deleteResource(Long resourceId) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "DELETE FROM resource WHERE (id = ?);";
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, resourceId);

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
