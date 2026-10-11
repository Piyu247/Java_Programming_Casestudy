/**
 * Leave Class
 * Represents a basic leave type in the system.
 */
public class Leave {
    // Fields
    private String leaveId;
    private String leaveType;
    private int totalDays;

    // Default Constructor
    public Leave() {
    }

    // Parameterized Constructor
    public Leave(String leaveId, String leaveType, int totalDays) {
        this.leaveId = leaveId;
        this.leaveType = leaveType;
        this.totalDays = totalDays;
    }

    // Getters and Setters
    public String getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(String leaveId) {
        this.leaveId = leaveId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    // Helper method to display leave details
    @Override
    public String toString() {
        return leaveId + " | " + leaveType + " (" + totalDays + " days max)";
    }
}
