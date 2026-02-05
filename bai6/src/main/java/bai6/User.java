package bai6;

public class User {
    private final String username;
    private final String password;
    private final String fullName;
    private final String email;
    private final String createdAt;

    public User(String username, String password, String fullName, String email, String createdAt) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
