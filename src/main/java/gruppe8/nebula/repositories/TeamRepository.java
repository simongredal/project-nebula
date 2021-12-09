package gruppe8.nebula.repositories;

import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.services.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;


import java.util.List;

@Repository
public class TeamRepository {
    private final DatabaseManager databaseManager;
    private final Logger log;

    public TeamRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    public TeamEntity createTeam(TeamEntity entity){
        String query = "INSERT INTO teams (name) VALUES (:name)";
        try (Connection connection = databaseManager.beginTransaction()){
            connection.createQuery(query, true)
                    .addParameter("name", entity.getName())
                    .executeUpdate();

            if (connection.getResult() == 1) {
                connection.commit();
                return getTeamById( connection.getKey(Long.class) );
            }
        } catch (Sql2oException e){
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error(currentMethod + "%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
        }
        return null;
    }

    public TeamEntity getTeamById(Long id) {
        String query = "SELECT * FROM teams WHERE id = :id";

        try (Connection connection = databaseManager.openConnection()) {
            TeamEntity entity = connection.createQuery(query)
                    .addParameter("id", id)
                    .setAutoDeriveColumnNames(true)
                    .executeAndFetchFirst(TeamEntity.class);

                return entity;
        } catch (Sql2oException e) {
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error(currentMethod + "%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
        }

        return null;
    }

    public boolean deleteTeam(Long teamId) {
        String query = "DELETE FROM teams WHERE (id = :id);";
        try (Connection connection = databaseManager.beginTransaction()) {
            connection.createQuery(query)
                    .addParameter("id", teamId)
                    .executeUpdate();

            if (connection.getResult() == 1) {
                connection.commit();
                return true;
            }
        } catch (Sql2oException e) {
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error(currentMethod + "%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
        }
        return false;
    }

    public List<TeamEntity> getAllTeams() {
        String query = "SELECT * FROM teams";

        try (Connection connection = databaseManager.openConnection()) {
            return connection.createQuery(query)
                    .setAutoDeriveColumnNames(true)
                    .executeAndFetch(TeamEntity.class);
        } catch (Sql2oException e) {
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error(currentMethod + "%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
        }

        return null;
    }
}
