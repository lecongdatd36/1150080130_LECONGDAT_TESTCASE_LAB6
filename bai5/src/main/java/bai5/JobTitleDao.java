package bai5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobTitleDao {
    private final DbConfig config;

    public JobTitleDao(DbConfig config) {
        this.config = config;
    }

    public boolean existsByJobCode(String jobCode) throws SQLException {
        String sql = "SELECT 1 FROM job_title WHERE job_code = ?";
        try (Connection connection = DbConnection.getConnection(config);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, jobCode);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void insert(JobTitle jobTitle) throws SQLException {
        String sql = "INSERT INTO job_title (job_code, job_title, job_description, job_specification, note) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection(config);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, jobTitle.getJobCode());
            statement.setString(2, jobTitle.getJobTitle());
            statement.setString(3, jobTitle.getJobDescription());
            statement.setString(4, jobTitle.getJobSpecification());
            statement.setString(5, jobTitle.getNote());
            statement.executeUpdate();
        }
    }
}
