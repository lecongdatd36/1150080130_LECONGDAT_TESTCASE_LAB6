package bai4;

public class OrganizationUnit {
    private final String unitId;
    private final String name;
    private final String description;

    public OrganizationUnit(String unitId, String name, String description) {
        this.unitId = unitId;
        this.name = name;
        this.description = description;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
