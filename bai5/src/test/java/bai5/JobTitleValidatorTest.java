package bai5;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JobTitleValidatorTest {
    @Test
    public void validDataPasses() {
        ValidationResult result = JobTitleValidator.validate("JT001", "Developer", "Desc", "Note");
        assertTrue(result.isValid());
    }

    @Test
    public void jobCodeRequired() {
        ValidationResult result = JobTitleValidator.validate("", "Developer", "", "");
        assertFalse(result.isValid());
        assertEquals("Job Code is required", result.getMessage());
    }

    @Test
    public void jobTitleRequired() {
        ValidationResult result = JobTitleValidator.validate("JT002", "", "", "");
        assertFalse(result.isValid());
        assertEquals("Job Title is required", result.getMessage());
    }

    @Test
    public void jobCodeTooLong() {
        ValidationResult result = JobTitleValidator.validate("123456789012345678901", "Developer", "", "");
        assertFalse(result.isValid());
        assertEquals("Job Code must be <= 20 characters", result.getMessage());
    }

    @Test
    public void jobTitleTooLong() {
        String longTitle = "A".repeat(101);
        ValidationResult result = JobTitleValidator.validate("JT003", longTitle, "", "");
        assertFalse(result.isValid());
        assertEquals("Job Title must be <= 100 characters", result.getMessage());
    }

    @Test
    public void descriptionTooLong() {
        String longDesc = "A".repeat(501);
        ValidationResult result = JobTitleValidator.validate("JT004", "Developer", longDesc, "");
        assertFalse(result.isValid());
        assertEquals("Job Description must be <= 500 characters", result.getMessage());
    }

    @Test
    public void noteTooLong() {
        String longNote = "A".repeat(501);
        ValidationResult result = JobTitleValidator.validate("JT005", "Developer", "", longNote);
        assertFalse(result.isValid());
        assertEquals("Note must be <= 500 characters", result.getMessage());
    }

    @Test
    public void invalidJobCodeCharacters() {
        ValidationResult result = JobTitleValidator.validate("JT#01", "Developer", "", "");
        assertFalse(result.isValid());
        assertEquals("Job Code has invalid characters", result.getMessage());
    }

    @Test
    public void trimsWhitespace() {
        ValidationResult result = JobTitleValidator.validate("  JT006  ", "  Dev  ", "  Desc  ", "  Note  ");
        assertTrue(result.isValid());
    }
}
