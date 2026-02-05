package bai6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final DbConfig config;

    public UserDao(DbConfig config) {
        this.config = config;
    }

    public boolean existsByUsername(String username) throws SQLException {
        String sql = "SELECT 1 FROM app_user WHERE username = ?";
        try (Connection connection = DbConnection.getConnection(config);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO app_user (username, password, full_name, email) VALUES (?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection(config);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFullName());
            statement.setString(4, user.getEmail());
            statement.executeUpdate();
        }
    }

    public boolean update(User user) throws SQLException {
        String sql = "UPDATE app_user SET password = ?, full_name = ?, email = ? WHERE username = ?";
        try (Connection connection = DbConnection.getConnection(config);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getFullName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUsername());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteByUsername(String username) throws SQLException {
        String sql = "DELETE FROM app_user WHERE username = ?";
        try (Connection connection = DbConnection.getConnection(config);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            return statement.executeUpdate() > 0;
        }
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT username, password, full_name, email, created_at FROM app_user ORDER BY created_at DESC";
        List<User> users = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection(config);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("created_at"))
                );
            }
        }
        return users;
    }
}
