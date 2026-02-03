package bai4;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrganizationUnitValidatorTest {
    @Test
    public void validDataPasses() {
        ValidationResult result = OrganizationUnitValidator.validate("OU001", "HR", "Human Resource");
        assertTrue(result.isValid());
    }

    @Test
    public void unitIdRequired() {
        ValidationResult result = OrganizationUnitValidator.validate("", "HR", "");
        assertFalse(result.isValid());
        assertEquals("Unit Id is required", result.getMessage());
    }

    @Test
    public void nameRequired() {
        ValidationResult result = OrganizationUnitValidator.validate("OU002", "", "");
        assertFalse(result.isValid());
        assertEquals("Name is required", result.getMessage());
    }

    @Test
    public void unitIdTooLong() {
        ValidationResult result = OrganizationUnitValidator.validate("123456789012345678901", "HR", "");
        assertFalse(result.isValid());
        assertEquals("Unit Id must be <= 20 characters", result.getMessage());
    }

    @Test
    public void nameTooLong() {
        String longName = "A".repeat(101);
        ValidationResult result = OrganizationUnitValidator.validate("OU003", longName, "");
        assertFalse(result.isValid());
        assertEquals("Name must be <= 100 characters", result.getMessage());
    }

    @Test
    public void descriptionTooLong() {
        String longDesc = "A".repeat(501);
        ValidationResult result = OrganizationUnitValidator.validate("OU004", "HR", longDesc);
        assertFalse(result.isValid());
        assertEquals("Description must be <= 500 characters", result.getMessage());
    }

    @Test
    public void invalidUnitIdCharacters() {
        ValidationResult result = OrganizationUnitValidator.validate("OU#01", "HR", "");
        assertFalse(result.isValid());
        assertEquals("Unit Id has invalid characters", result.getMessage());
    }

    @Test
    public void trimsWhitespace() {
        ValidationResult result = OrganizationUnitValidator.validate("  OU005  ", "  HR  ", "  desc  ");
        assertTrue(result.isValid());
    }
}
