// Authors Simon Gredal & Malthe Gram & Mark Friis Larsen & Frederik Vilhelmsen
package gruppe8.nebula.repositories;

import gruppe8.nebula.entities.ResourceEntity;
import gruppe8.nebula.services.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ResourceRepository {
    private final DatabaseManager databaseManager;
    private final Logger log;

    public ResourceRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.log = LoggerFactory.getLogger(this.getClass());
    }
    public List<ResourceEntity> getResourcesForProject(Long projectId) {
        List<ResourceEntity> resources = new ArrayList<>();
        try (Connection connection = databaseManager.getConnection()) {
            String query = "SELECT * FROM resources WHERE project_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, projectId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resources.add(new ResourceEntity(
                        resultSet.getLong("id"),
                        resultSet.getLong("project_id"),
                        resultSet.getString("name"),
                        resultSet.getString("color")
                ));
            }
        } catch (SQLException e) {
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error("%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
        }

        return resources;
    }
    public boolean createResource(ResourceEntity resource){
        try(Connection connection = databaseManager.getConnection()){
            String query = "insert into resources (project_id,name,color) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1,resource.project_id());
            preparedStatement.setString(2,resource.name());
            preparedStatement.setString(3,resource.color());

            preparedStatement.execute();

            return preparedStatement.getUpdateCount() == 1;

        } catch (SQLException e){
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error("%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
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
            log.error(e.getMessage());
        }
        return false;
    }

    public boolean deleteResource(Long resourceId) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "DELETE FROM resources WHERE (id = ?);";
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
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error("%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
        }
        return false;
    }


}
