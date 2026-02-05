package bai6;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DbConfig {
    private final String url;
    private final String user;
    private final String pass;

    public DbConfig(String propertiesPath) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(propertiesPath)) {
            properties.load(input);
        }
        this.url = properties.getProperty("jdbc.url");
        this.user = properties.getProperty("jdbc.user");
        this.pass = properties.getProperty("jdbc.pass");
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }
}
