package bai6;

public class UserValidator {
    private UserValidator() {
    }

    public static ValidationResult validate(String username, String password, String fullName, String email) {
        if (username == null || username.trim().isEmpty()) {
            return ValidationResult.error("Username is required");
        }
        if (password == null || password.trim().isEmpty()) {
            return ValidationResult.error("Password is required");
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            return ValidationResult.error("Full name is required");
        }
        if (email == null || email.trim().isEmpty()) {
            return ValidationResult.error("Email is required");
        }

        String trimmedUsername = username.trim();
        String trimmedPassword = password.trim();
        String trimmedFullName = fullName.trim();
        String trimmedEmail = email.trim();

        if (trimmedUsername.length() > 20) {
            return ValidationResult.error("Username must be <= 20 characters");
        }
        if (trimmedPassword.length() > 50) {
            return ValidationResult.error("Password must be <= 50 characters");
        }
        if (trimmedFullName.length() > 100) {
            return ValidationResult.error("Full name must be <= 100 characters");
        }
        if (trimmedEmail.length() > 100) {
            return ValidationResult.error("Email must be <= 100 characters");
        }
        if (!trimmedUsername.matches("[A-Za-z0-9_]+")) {
            return ValidationResult.error("Username has invalid characters");
        }
        if (!trimmedEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return ValidationResult.error("Email is invalid");
        }
        return ValidationResult.ok();
    }
}
