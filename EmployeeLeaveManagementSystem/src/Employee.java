/**
 * Employee Class
 * Represents an employee with leave balance and personal information.
 */
public class Employee {
    // Fields
    private String employeeId;
    private String name;
    private String email;
    private String department;
    private int leaveBalance;

    // Default Constructor
    public Employee() {
    }

    // Parameterized Constructor
    public Employee(String employeeId, String name, String email, String department, int leaveBalance) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.department = department;
        this.leaveBalance = leaveBalance;
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(int leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    // Helper method to display formatted employee info
    @Override
    public String toString() {
        return "ID: " + employeeId + " | Name: " + name + " | Email: " + email + 
               " | Dept: " + department + " | Leave Balance: " + leaveBalance + " days";
    }
}
