/**
 * Department Class
 * Represents a department in the organization.
 */
public class Department {
    // Fields
    private String departmentId;
    private String departmentName;

    // Default Constructor
    public Department() {
    }

    // Parameterized Constructor
    public Department(String departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    // Getters and Setters
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    // Helper method to display department details
    @Override
    public String toString() {
        return departmentId + " - " + departmentName;
    }
}
