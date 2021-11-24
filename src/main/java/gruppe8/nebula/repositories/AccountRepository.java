package gruppe8.nebula.repositories;


import gruppe8.nebula.services.DatabaseManager;
import gruppe8.nebula.models.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AccountRepository implements UserDetailsService {
    private final DatabaseManager databaseManager;
    private final Logger logger;

    public AccountRepository(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public boolean createAccount(Account account){

        try(Connection connection = databaseManager.getConnection()){
            String query = "insert into Nebula.Accounts (name, password, email) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,account.getName());
            preparedStatement.setString(2,account.getPassword());
            preparedStatement.setString(3,account.getEmail());

            preparedStatement.execute();
            return preparedStatement.getUpdateCount() == 1;

        }catch (SQLException e){
            logger.error(e.getMessage());
        }
        return false;
    }


    @Override
    // Username is actually Account, but since it's an interface we cannot change the method name.
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        if (!accountExists(name)) throw new UsernameNotFoundException(name);

        String query =  """
                        SELECT id, email, name, password
                        FROM Nebula.accounts
                        WHERE email LIKE ?
                        """;
        Account account = null;

        try (Connection connection = databaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())  {
                 account = new Account(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
        } catch (SQLException e){
            logger.error(e.getMessage());
        }
        return account;
    }

    public boolean accountExists(String name) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "SELECT EXISTS(SELECT email FROM Nebula.Accounts WHERE email LIKE ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            return resultSet.getInt(1) == 1;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

}

