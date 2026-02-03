package bai4;

import org.junit.Test;

import java.nio.file.Path;
import java.sql.Connection;

import static org.junit.Assert.assertNotNull;

public class DbConnectionTest {
    @Test
    public void canConnectToSqlServer() throws Exception {
        Path propsPath = Path.of("bai4", "db.properties");
        DbConfig config = new DbConfig(propsPath.toString());

        try (Connection connection = DbConnection.getConnection(config)) {
            assertNotNull(connection);
        }
    }
}
