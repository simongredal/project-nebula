package gruppe8.nebula.repositories;

import gruppe8.nebula.entities.AccountEntity;
import gruppe8.nebula.services.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

@Repository
public class AccountRepository {
    private final DatabaseManager databaseManager;
    private final Logger log;

    public AccountRepository(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    public boolean createAccount(AccountEntity account){
        String query = "INSERT INTO accounts (email, password, name)" +
                        "VALUES (:email, :password, :name)";
        try (Connection connection = databaseManager.beginTransaction()) {
            connection.createQuery(query)
                    .bind(account)
                    .executeUpdate();

            if (connection.getResult() == 1){
                connection.commit();
                return true;
            }
        } catch (Sql2oException e){
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error(currentMethod + "%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
        }
        return false;
    }

    public AccountEntity getAccountByEmail(String email) {
        String query = "SELECT * FROM accounts WHERE email LIKE :email";
        try (Connection connection = databaseManager.openConnection()) {
            AccountEntity entity = connection.createQuery(query)
                    .addParameter("email", email)
                    .setAutoDeriveColumnNames(true)
                    .executeAndFetchFirst(AccountEntity.class);
            return entity;
        } catch (Sql2oException e) {
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error(currentMethod + "%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
        }
        return null;
    }

    public List<AccountEntity> getAllAccounts() {
        String query = "SELECT * FROM accounts";
        try (Connection  connection = databaseManager.openConnection()) {
            return connection.createQuery(query)
                    .executeAndFetch(AccountEntity.class);
        } catch (Sql2oException e) {
            String currentMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
            log.error(currentMethod + "%s() threw exception with message '%s'".formatted(currentMethod, e.getMessage()));
        }
        return null;
    }
}

