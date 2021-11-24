package gruppe8.nebula.repositories;


import gruppe8.nebula.DatabaseManager;
import gruppe8.nebula.models.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class AccountRepository implements UserDetailsService {
    private final DatabaseManager databaseManager;
    private final Logger logger;

    public AccountRepository(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public boolean addAccount(Account account){

        try(Connection connection = databaseManager.getConnection()){
            String query = "insert into Nebula.Accounts (name, password, email) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,account.getName());
            preparedStatement.setString(2,account.getPassword());
            preparedStatement.setString(3,account.getEmail());

            preparedStatement.execute();


        }catch (SQLException e){
            logger.error(e.getMessage());
        }
        return false;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
