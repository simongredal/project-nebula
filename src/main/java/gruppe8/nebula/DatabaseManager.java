package gruppe8.nebula;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class DatabaseManager {
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/recursion", "simon", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
