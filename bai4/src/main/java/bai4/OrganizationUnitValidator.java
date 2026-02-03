package bai4;

public class OrganizationUnitValidator {
    private OrganizationUnitValidator() {
    }

    public static ValidationResult validate(String unitId, String name, String description) {
        if (unitId == null || unitId.trim().isEmpty()) {
            return ValidationResult.error("Unit Id is required");
        }
        if (name == null || name.trim().isEmpty()) {
            return ValidationResult.error("Name is required");
        }
        String trimmedUnitId = unitId.trim();
        String trimmedName = name.trim();
        String trimmedDescription = description == null ? "" : description.trim();

        if (trimmedUnitId.length() > 20) {
            return ValidationResult.error("Unit Id must be <= 20 characters");
        }
        if (trimmedName.length() > 100) {
            return ValidationResult.error("Name must be <= 100 characters");
        }
        if (trimmedDescription.length() > 500) {
            return ValidationResult.error("Description must be <= 500 characters");
        }
        if (!trimmedUnitId.matches("[A-Za-z0-9_-]+")) {
            return ValidationResult.error("Unit Id has invalid characters");
        }
        return ValidationResult.ok();
    }
}
