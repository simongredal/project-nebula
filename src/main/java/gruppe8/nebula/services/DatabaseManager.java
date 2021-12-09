package gruppe8.nebula.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.DriverManager;
import java.sql.SQLException;

@Service
@Scope("singleton")
public class DatabaseManager {
    private final String url;
    private final String username;
    private final String password;

    private final Sql2o sql2o;
    private final Logger log;

    // Spring will automatically supply us with the Environment object.
    private DatabaseManager(Environment env) {
        this.url = env.getProperty("spring.datasource.url");
        this.username = env.getProperty("spring.datasource.username");
        this.password = env.getProperty("spring.datasource.password");

        this.sql2o = new Sql2o(url, username, password);
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    public java.sql.Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public Connection openConnection() {
        return sql2o.open();
    }

    public Connection beginTransaction() {
        return sql2o.beginTransaction();
    }
}