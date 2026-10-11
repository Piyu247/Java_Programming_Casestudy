/**
 * LeaveRequest Class
 * Represents a leave application submitted by an employee.
 */
public class LeaveRequest {
    // Fields
    private String requestId;
    private String employeeId;
    private String leaveType;
    private int days;
    private String status; // "Pending", "Approved", "Rejected"
    private String reason;

    // Default Constructor
    public LeaveRequest() {
    }

    // Parameterized Constructor
    public LeaveRequest(String requestId, String employeeId, String leaveType, int days, String status, String reason) {
        this.requestId = requestId;
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.days = days;
        this.status = status;
        this.reason = reason;
    }

    // Getters and Setters
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    // Helper method to display leave request info
    @Override
    public String toString() {
        return "Req ID: " + requestId + " | Emp ID: " + employeeId + " | Type: " + leaveType + 
               " | Days: " + days + " | Status: " + status + " | Reason: " + reason;
    }
}
