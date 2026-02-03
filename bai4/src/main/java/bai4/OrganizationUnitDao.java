package bai4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizationUnitDao {
    private final DbConfig config;

    public OrganizationUnitDao(DbConfig config) {
        this.config = config;
    }

    public boolean existsByUnitId(String unitId) throws SQLException {
        String sql = "SELECT 1 FROM organization_unit WHERE unit_id = ?";
        try (Connection connection = DbConnection.getConnection(config);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, unitId);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void insert(OrganizationUnit unit) throws SQLException {
        String sql = "INSERT INTO organization_unit (unit_id, name, description) VALUES (?, ?, ?)";
        try (Connection connection = DbConnection.getConnection(config);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, unit.getUnitId());
            statement.setString(2, unit.getName());
            statement.setString(3, unit.getDescription());
            statement.executeUpdate();
        }
    }
}
