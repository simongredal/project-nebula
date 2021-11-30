package gruppe8.nebula.repositories;

import gruppe8.nebula.entities.MembershipEntity;
import gruppe8.nebula.entities.TeamEntity;
import gruppe8.nebula.models.Account;
import gruppe8.nebula.services.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public List<MembershipEntity> getMemberships(Long accountId, Boolean membershipAccepted) {
        List<MembershipEntity> memberships = new ArrayList<>();
        try (Connection connection = databaseManager.getConnection()) {
            String query = "SELECT * FROM membership_view WHERE account_id = ? AND membership_accepted = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, accountId);
            preparedStatement.setBoolean(2, membershipAccepted);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next() ) {
                memberships.add( new MembershipEntity(
                        resultSet.getLong("account_id"),
                        resultSet.getString("account_name"),
                        resultSet.getString("account_email"),
                        resultSet.getLong("team_id"),
                        resultSet.getString("team_name"),
                        resultSet.getLong("membership_id"),
                        resultSet.getBoolean("membership_accepted"),
                        resultSet.getInt("membership_count")
                ));
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return Collections.unmodifiableList(memberships);
    }

    public Boolean accountOwnsMembership(Long accountId, Long membershipId) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "SELECT EXISTS(SELECT id FROM memberships WHERE (id = ? AND account_id = ?));";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, membershipId);
            preparedStatement.setLong(2, accountId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) == 1;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return false;
    }

    public Boolean rejectInvitation(Long membershipId) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "DELETE FROM memberships WHERE id =?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, membershipId);

            preparedStatement.execute();
            return preparedStatement.getUpdateCount() == 1;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return false;
    }
}