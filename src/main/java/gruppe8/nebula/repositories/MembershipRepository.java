package gruppe8.nebula.repositories;

import gruppe8.nebula.models.Account;
import gruppe8.nebula.models.Membership;
import gruppe8.nebula.models.Team;
import gruppe8.nebula.services.DatabaseManager;
import gruppe8.nebula.entities.ProjectEntity;
import gruppe8.nebula.entities.MembershipEntity;
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
public class MembershipRepository{
    private final DatabaseManager databaseManager;
    private final Logger logger;

    public MembershipRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public boolean createMembership(Team team, Account account){
        try(Connection connection = databaseManager.getConnection()){
            String query = "insert into Nebula.memberships (team_id,account_id,accepted) VALUES (?,?,false)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1,team.getId());
            preparedStatement.setLong(2,account.getId());

            preparedStatement.execute();

            return preparedStatement.getUpdateCount() == 1;

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }
}