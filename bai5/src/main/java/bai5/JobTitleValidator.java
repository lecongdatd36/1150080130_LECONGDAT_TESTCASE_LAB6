package bai5;

public class JobTitleValidator {
    private JobTitleValidator() {
    }

    public static ValidationResult validate(String code, String title, String description, String note) {
        if (code == null || code.trim().isEmpty()) {
            return ValidationResult.error("Job Code is required");
        }
        if (title == null || title.trim().isEmpty()) {
            return ValidationResult.error("Job Title is required");
        }
        String trimmedCode = code.trim();
        String trimmedTitle = title.trim();
        String trimmedDescription = description == null ? "" : description.trim();
        String trimmedNote = note == null ? "" : note.trim();

        if (trimmedCode.length() > 20) {
            return ValidationResult.error("Job Code must be <= 20 characters");
        }
        if (trimmedTitle.length() > 100) {
            return ValidationResult.error("Job Title must be <= 100 characters");
        }
        if (trimmedDescription.length() > 500) {
            return ValidationResult.error("Job Description must be <= 500 characters");
        }
        if (trimmedNote.length() > 500) {
            return ValidationResult.error("Note must be <= 500 characters");
        }
        if (!trimmedCode.matches("[A-Za-z0-9_-]+")) {
            return ValidationResult.error("Job Code has invalid characters");
        }
        return ValidationResult.ok();
    }
}
