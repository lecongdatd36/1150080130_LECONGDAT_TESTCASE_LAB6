package bai6;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserValidatorTest {
    @Test
    public void validDataPasses() {
        ValidationResult result = UserValidator.validate("admin01", "123456", "Nguyen Van A", "a@gmail.com");
        assertTrue(result.isValid());
    }

    @Test
    public void usernameRequired() {
        ValidationResult result = UserValidator.validate("", "123456", "Nguyen Van A", "a@gmail.com");
        assertFalse(result.isValid());
        assertEquals("Username is required", result.getMessage());
    }

    @Test
    public void passwordRequired() {
        ValidationResult result = UserValidator.validate("admin01", "", "Nguyen Van A", "a@gmail.com");
        assertFalse(result.isValid());
        assertEquals("Password is required", result.getMessage());
    }

    @Test
    public void fullNameRequired() {
        ValidationResult result = UserValidator.validate("admin01", "123456", "", "a@gmail.com");
        assertFalse(result.isValid());
        assertEquals("Full name is required", result.getMessage());
    }

    @Test
    public void emailRequired() {
        ValidationResult result = UserValidator.validate("admin01", "123456", "Nguyen Van A", "");
        assertFalse(result.isValid());
        assertEquals("Email is required", result.getMessage());
    }

    @Test
    public void usernameTooLong() {
        String longUsername = "A".repeat(21);
        ValidationResult result = UserValidator.validate(longUsername, "123456", "Nguyen Van A", "a@gmail.com");
        assertFalse(result.isValid());
        assertEquals("Username must be <= 20 characters", result.getMessage());
    }

    @Test
    public void passwordTooLong() {
        String longPassword = "A".repeat(51);
        ValidationResult result = UserValidator.validate("admin01", longPassword, "Nguyen Van A", "a@gmail.com");
        assertFalse(result.isValid());
        assertEquals("Password must be <= 50 characters", result.getMessage());
    }

    @Test
    public void fullNameTooLong() {
        String longName = "A".repeat(101);
        ValidationResult result = UserValidator.validate("admin01", "123456", longName, "a@gmail.com");
        assertFalse(result.isValid());
        assertEquals("Full name must be <= 100 characters", result.getMessage());
    }

    @Test
    public void emailTooLong() {
        String longEmail = "a".repeat(95) + "@t.com";
        ValidationResult result = UserValidator.validate("admin01", "123456", "Nguyen Van A", longEmail);
        assertFalse(result.isValid());
        assertEquals("Email must be <= 100 characters", result.getMessage());
    }

    @Test
    public void invalidUsernameCharacters() {
        ValidationResult result = UserValidator.validate("ad#min", "123456", "Nguyen Van A", "a@gmail.com");
        assertFalse(result.isValid());
        assertEquals("Username has invalid characters", result.getMessage());
    }

    @Test
    public void invalidEmailFormat() {
        ValidationResult result = UserValidator.validate("admin01", "123456", "Nguyen Van A", "abc");
        assertFalse(result.isValid());
        assertEquals("Email is invalid", result.getMessage());
    }

    @Test
    public void trimsWhitespace() {
        ValidationResult result = UserValidator.validate("  admin01  ", "  123456  ", "  Nguyen Van A  ", "  a@gmail.com  ");
        assertTrue(result.isValid());
    }
}
