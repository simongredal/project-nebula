package gruppe8.nebula.repositories;

import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.services.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class MembershipRepository{
    private final DatabaseManager databaseManager;
    private final Logger log;

    public MembershipRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    public boolean createMembership(TeamEntity team, Account account, Boolean accepted){
        try (Connection connection = databaseManager.getConnection()) {
            String query = "INSERT INTO memberships (team_id, account_id, accepted) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, team.id());
            preparedStatement.setLong(2, account.getId());
            preparedStatement.setBoolean(3, accepted);

            preparedStatement.execute();
            return preparedStatement.getUpdateCount() == 1;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return false;
    }
}