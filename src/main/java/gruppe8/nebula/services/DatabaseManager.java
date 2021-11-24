package gruppe8.nebula.services;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
@Scope("singleton")
public class DatabaseManager {
    private final String url;
    private final String username;
    private final String password;

    // Spring will automatically supply us with the Environment object.
    // The environment
    private DatabaseManager(Environment env) {
        this.url = env.getProperty("spring.datasource.url");
        this.username = env.getProperty("spring.datasource.username");
        this.password = env.getProperty("spring.datasource.password");
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(this.url, this.username, this.password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}