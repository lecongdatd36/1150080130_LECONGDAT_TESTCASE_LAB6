package bai6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private DbConnection() {
    }

    public static Connection getConnection(DbConfig config) throws SQLException {
        return DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPass());
    }
}
