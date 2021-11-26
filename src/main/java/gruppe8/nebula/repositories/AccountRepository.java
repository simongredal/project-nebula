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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class AccountRepository implements UserDetailsService {
    private final DatabaseManager databaseManager;
    private final Logger log;

    public AccountRepository(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    public boolean createAccount(Account account){
        try (Connection connection = databaseManager.getConnection()){
            String query = "insert into Nebula.Accounts (name, password, email) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,account.getName());
            preparedStatement.setString(2,account.getPassword());
            preparedStatement.setString(3,account.getEmail());

            preparedStatement.execute();
            return preparedStatement.getUpdateCount() == 1;
        } catch (SQLException e){
            log.error(e.getMessage());
        }
        return false;
    }

    public Account getAccountByEmail(String email) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "SELECT * FROM accounts WHERE email LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Account(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }


    @Override
    // Username is actually Account, but since it's an interface we cannot change the method name.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!accountExists(username)) throw new UsernameNotFoundException(username);

        String query =  "SELECT id, email, name, password FROM accounts WHERE email LIKE ?";

        try (Connection connection = databaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())  {
                 return new Account(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    public boolean accountExists(String name) {
        try (Connection connection = databaseManager.getConnection()) {
            String query = "SELECT EXISTS(SELECT email FROM accounts WHERE email LIKE ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            return resultSet.getInt(1) == 1;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = databaseManager.getConnection()) {
            String query = "SELECT * FROM accounts";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                accounts.add(new Account(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        null
                ));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return Collections.unmodifiableList( accounts );
    }
}

