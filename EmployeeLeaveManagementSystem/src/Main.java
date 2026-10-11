import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Main Class - Employee Leave Management System GUI
 * 
 * Demonstrates Core Java concepts:
 * 1. OOP (Classes, Objects, Inheritance, Encapsulation)
 * 2. Array: Stores static leave type details.
 * 3. ArrayList: Stores employees and departments dynamically.
 * 4. LinkedList: Stores leave application request history in order.
 * 5. HashMap: Maps Employee ID to Employee for fast O(1) lookups.
 * 6. TreeMap: Keeps employees ordered by ID.
 * 7. Exception Handling & Validation.
 * 8. Swing GUI interface.
 */
public class Main extends JFrame {

    // --- REQUIRED JAVA COLLECTIONS AND DATA STRUCTURES ---

    // 1. Array: Fixed array of basic leave types
    private Leave[] leaveTypesArray = new Leave[] {
        new Leave("L01", "Casual Leave", 15),
        new Leave("L02", "Sick Leave", 10),
        new Leave("L03", "Earned Leave", 20)
    };

    // 2. ArrayList: Dynamic list for storing employees and departments
    private ArrayList<Employee> employeeList = new ArrayList<>();
    private ArrayList<Department> departmentList = new ArrayList<>();

    // 3. LinkedList: List to store leave request history in insertion order
    private LinkedList<LeaveRequest> leaveHistory = new LinkedList<>();

    // 4. HashMap: Key-Value storage mapping Employee ID to Employee object
    private HashMap<String, Employee> employeeMap = new HashMap<>();

    // 5. TreeMap: Sorted map of employees by ID
    private TreeMap<String, Employee> sortedEmployeeMap = new TreeMap<>();

    // Auto-increment request ID counter
    private int nextRequestId = 101;

    // --- SWING GUI COMPONENTS ---
    private JTextField txtEmpId, txtEmpName, txtEmpEmail, txtDepartment, txtLeaveDays, txtReason, txtRequestId;
    private JComboBox<String> comboLeaveType;
    private JTextArea displayArea;

    // Constructor to initialize GUI and pre-populate sample data
    public Main() {
        // Set window title and dimensions
        setTitle("Employee Leave Management System");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Pre-populate sample departments and employees
        seedInitialData();

        // Create main layout
        setLayout(new BorderLayout(10, 10));

        // --- TOP PANEL: Form Input Fields ---
        JPanel inputPanel = new JPanel(new GridLayout(4, 4, 8, 8));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Employee & Leave Input Form"));

        inputPanel.add(new JLabel("Employee ID:"));
        txtEmpId = new JTextField();
        inputPanel.add(txtEmpId);

        inputPanel.add(new JLabel("Employee Name:"));
        txtEmpName = new JTextField();
        inputPanel.add(txtEmpName);

        inputPanel.add(new JLabel("Email:"));
        txtEmpEmail = new JTextField();
        inputPanel.add(txtEmpEmail);

        inputPanel.add(new JLabel("Department:"));
        txtDepartment = new JTextField();
        inputPanel.add(txtDepartment);

        inputPanel.add(new JLabel("Leave Type:"));
        comboLeaveType = new JComboBox<>(new String[]{"Casual Leave", "Sick Leave", "Earned Leave"});
        inputPanel.add(comboLeaveType);

        inputPanel.add(new JLabel("Leave Days:"));
        txtLeaveDays = new JTextField();
        inputPanel.add(txtLeaveDays);

        inputPanel.add(new JLabel("Request ID (Approve/Reject):"));
        txtRequestId = new JTextField();
        inputPanel.add(txtRequestId);

        inputPanel.add(new JLabel("Reason / Remarks:"));
        txtReason = new JTextField();
        inputPanel.add(txtReason);

        add(inputPanel, BorderLayout.NORTH);

        // --- CENTER PANEL: Output Display ---
        displayArea = new JTextArea();
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output Display Console"));
        add(scrollPane, BorderLayout.CENTER);

        // --- SOUTH PANEL: Action Buttons ---
        JPanel buttonPanel = new JPanel(new GridLayout(2, 5, 6, 6));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Operations"));

        JButton btnAddEmp = new JButton("Add Employee");
        JButton btnViewEmp = new JButton("View Employees");
        JButton btnUpdateEmp = new JButton("Update Employee");
        JButton btnDeleteEmp = new JButton("Delete Employee");
        JButton btnSearchEmp = new JButton("Search Employee");
        JButton btnApplyLeave = new JButton("Apply Leave");
        JButton btnApproveLeave = new JButton("Approve Leave");
        JButton btnRejectLeave = new JButton("Reject Leave");
        JButton btnCheckBalance = new JButton("View Leave Balance");
        JButton btnGenerateReport = new JButton("Generate Report");

        // Add buttons to panel
        buttonPanel.add(btnAddEmp);
        buttonPanel.add(btnViewEmp);
        buttonPanel.add(btnUpdateEmp);
        buttonPanel.add(btnDeleteEmp);
        buttonPanel.add(btnSearchEmp);
        buttonPanel.add(btnApplyLeave);
        buttonPanel.add(btnApproveLeave);
        buttonPanel.add(btnRejectLeave);
        buttonPanel.add(btnCheckBalance);
        buttonPanel.add(btnGenerateReport);

        add(buttonPanel, BorderLayout.SOUTH);

        // --- BUTTON EVENT LISTENERS ---
        btnAddEmp.addActionListener(e -> addEmployee());
        btnViewEmp.addActionListener(e -> viewEmployees());
        btnUpdateEmp.addActionListener(e -> updateEmployee());
        btnDeleteEmp.addActionListener(e -> deleteEmployee());
        btnSearchEmp.addActionListener(e -> searchEmployee());
        btnApplyLeave.addActionListener(e -> applyLeave());
        btnApproveLeave.addActionListener(e -> approveLeave());
        btnRejectLeave.addActionListener(e -> rejectLeave());
        btnCheckBalance.addActionListener(e -> viewLeaveBalance());
        btnGenerateReport.addActionListener(e -> generateReport());

        // Welcome message on startup
        displayArea.setText("========================================================================\n" +
                           "          WELCOME TO EMPLOYEE LEAVE MANAGEMENT SYSTEM                   \n" +
                           "========================================================================\n" +
                           "System ready. Sample data loaded into ArrayList, HashMap, and LinkedList.\n" +
                           "Click 'View Employees' to check pre-loaded records or use the buttons below.\n\n");
    }

    /**
     * Method to seed sample data into ArrayList, HashMap, and LinkedList on startup.
     */
    private void seedInitialData() {
        // Sample Departments
        departmentList.add(new Department("D01", "Information Technology"));
        departmentList.add(new Department("D02", "Human Resources"));

        // Sample Employees
        Employee e1 = new Employee("E101", "Alice Smith", "alice@company.com", "Information Technology", 20);
        Employee e2 = new Employee("E102", "Bob Johnson", "bob@company.com", "Human Resources", 15);
        Employee e3 = new Employee("E103", "Charlie Brown", "charlie@company.com", "Information Technology", 12);

        // Store in ArrayList
        employeeList.add(e1);
        employeeList.add(e2);
        employeeList.add(e3);

        // Store in HashMap and TreeMap
        employeeMap.put(e1.getEmployeeId(), e1);
        employeeMap.put(e2.getEmployeeId(), e2);
        employeeMap.put(e3.getEmployeeId(), e3);

        sortedEmployeeMap.put(e1.getEmployeeId(), e1);
        sortedEmployeeMap.put(e2.getEmployeeId(), e2);
        sortedEmployeeMap.put(e3.getEmployeeId(), e3);

        // Sample Leave Request in LinkedList
        LeaveRequest req1 = new LeaveRequest("REQ" + nextRequestId++, "E101", "Casual Leave", 2, "Approved", "Personal work");
        e1.setLeaveBalance(e1.getLeaveBalance() - 2);
        leaveHistory.add(req1);
    }

    /**
     * Operation 1: Add a new employee
     * Validates input fields and adds employee to ArrayList, HashMap, and TreeMap.
     */
    private void addEmployee() {
        try {
            String empId = txtEmpId.getText().trim();
            String name = txtEmpName.getText().trim();
            String email = txtEmpEmail.getText().trim();
            String dept = txtDepartment.getText().trim();

            // Validation checks
            if (empId.isEmpty()) {
                throw new Exception("Validation Error: Employee ID cannot be empty!");
            }
            if (name.isEmpty()) {
                throw new Exception("Validation Error: Employee Name cannot be empty!");
            }
            if (employeeMap.containsKey(empId)) {
                throw new Exception("Error: Employee ID '" + empId + "' already exists!");
            }

            // Default leave balance = 20 days
            int defaultBalance = 20;

            // Create new Employee object
            Employee newEmp = new Employee(empId, name, email, dept.isEmpty() ? "General" : dept, defaultBalance);

            // Store in collections
            employeeList.add(newEmp);
            employeeMap.put(empId, newEmp);
            sortedEmployeeMap.put(empId, newEmp);

            displayArea.setText("SUCCESS: Employee Added Successfully!\n\n" + newEmp.toString());
            clearInputFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Operation 2: View all employees
     * Sorts employees by name and displays them from ArrayList and TreeMap.
     */
    private void viewEmployees() {
        if (employeeList.isEmpty()) {
            displayArea.setText("No employees found in the system.");
            return;
        }

        // Sort employees by name using Collections.sort and Comparator
        sortEmployeesByName();

        StringBuilder sb = new StringBuilder();
        sb.append("========================================================================\n");
        sb.append("                       ALL EMPLOYEES (Sorted by Name)                   \n");
        sb.append("========================================================================\n");
        for (Employee emp : employeeList) {
            sb.append(emp.toString()).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    /**
     * Operation 3: Update existing employee details
     */
    private void updateEmployee() {
        try {
            String empId = txtEmpId.getText().trim();
            if (empId.isEmpty()) {
                throw new Exception("Validation Error: Please enter Employee ID to update!");
            }

            // Exception Handling: Check if employee exists in HashMap
            if (!employeeMap.containsKey(empId)) {
                throw new Exception("Error: Employee with ID '" + empId + "' does not exist!");
            }

            Employee emp = employeeMap.get(empId);
            String name = txtEmpName.getText().trim();
            String email = txtEmpEmail.getText().trim();
            String dept = txtDepartment.getText().trim();

            if (!name.isEmpty()) emp.setName(name);
            if (!email.isEmpty()) emp.setEmail(email);
            if (!dept.isEmpty()) emp.setDepartment(dept);

            displayArea.setText("SUCCESS: Employee Details Updated!\n\n" + emp.toString());
            clearInputFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Operation 4: Delete an employee by ID
     */
    private void deleteEmployee() {
        try {
            String empId = txtEmpId.getText().trim();
            if (empId.isEmpty()) {
                throw new Exception("Validation Error: Please enter Employee ID to delete!");
            }

            if (!employeeMap.containsKey(empId)) {
                throw new Exception("Error: Employee ID '" + empId + "' not found!");
            }

            // Remove from HashMap, TreeMap, and ArrayList
            Employee removedEmp = employeeMap.remove(empId);
            sortedEmployeeMap.remove(empId);
            employeeList.remove(removedEmp);

            displayArea.setText("SUCCESS: Employee ID '" + empId + "' deleted from system.\n\n" + removedEmp.toString());
            clearInputFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Delete Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Operation 5: Search employee by ID or Name
     */
    private void searchEmployee() {
        String empId = txtEmpId.getText().trim();
        String name = txtEmpName.getText().trim();

        if (empId.isEmpty() && name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Employee ID or Name to search!", "Search Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("========================================================================\n");
        sb.append("                           SEARCH RESULTS                               \n");
        sb.append("========================================================================\n");

        boolean found = false;

        // Search by HashMap if ID is given
        if (!empId.isEmpty()) {
            if (employeeMap.containsKey(empId)) {
                sb.append("Found by Employee ID:\n").append(employeeMap.get(empId).toString()).append("\n");
                found = true;
            }
        } else {
            // Linear Search by Name in ArrayList
            for (Employee emp : employeeList) {
                if (emp.getName().equalsIgnoreCase(name) || emp.getName().toLowerCase().contains(name.toLowerCase())) {
                    sb.append("Found by Name:\n").append(emp.toString()).append("\n");
                    found = true;
                }
            }
        }

        if (!found) {
            sb.append("No employee matching the search criteria was found.\n");
        }

        displayArea.setText(sb.toString());
    }

    /**
     * Sorting Concept: Sort employee list alphabetically by Name
     */
    private void sortEmployeesByName() {
        Collections.sort(employeeList, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return e1.getName().compareToIgnoreCase(e2.getName());
            }
        });
    }

    /**
     * Operation 6: Apply for leave
     * Validates employee existence, leave days > 0, and available balance.
     */
    private void applyLeave() {
        try {
            String empId = txtEmpId.getText().trim();
            String daysStr = txtLeaveDays.getText().trim();
            String leaveType = (String) comboLeaveType.getSelectedItem();
            String reason = txtReason.getText().trim();

            if (empId.isEmpty()) {
                throw new Exception("Validation Error: Employee ID cannot be empty!");
            }

            // Validation: Employee must exist before applying
            if (!employeeMap.containsKey(empId)) {
                throw new Exception("Error: Employee ID '" + empId + "' does not exist in the system!");
            }

            if (daysStr.isEmpty()) {
                throw new Exception("Validation Error: Please enter leave days!");
            }

            int days;
            try {
                days = Integer.parseInt(daysStr);
            } catch (NumberFormatException nfe) {
                throw new Exception("Validation Error: Leave days must be a valid number!");
            }

            // Validation: Leave days must be greater than zero
            if (days <= 0) {
                throw new Exception("Validation Error: Leave days must be greater than zero!");
            }

            Employee emp = employeeMap.get(empId);

            // Validation: Sufficient leave balance
            if (days > emp.getLeaveBalance()) {
                throw new Exception("Insufficient Balance Error: Requested " + days + " days, but available balance is only " + emp.getLeaveBalance() + " days!");
            }

            // Create leave request with "Pending" status
            String reqId = "REQ" + nextRequestId++;
            LeaveRequest request = new LeaveRequest(reqId, empId, leaveType, days, "Pending", reason.isEmpty() ? "Not specified" : reason);

            // Store in LinkedList history
            leaveHistory.add(request);

            displayArea.setText("SUCCESS: Leave Application Submitted!\n\n" + request.toString() +
                               "\nNote: Status is 'Pending'. Use 'Approve Leave' with Request ID: " + reqId + " to finalize.");
            clearInputFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Application Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Operation 7: Approve leave
     * Reduces employee leave balance when approved.
     */
    private void approveLeave() {
        try {
            String reqId = txtRequestId.getText().trim();
            if (reqId.isEmpty()) {
                throw new Exception("Validation Error: Please enter Request ID to approve!");
            }

            LeaveRequest targetReq = null;

            // Search leave history LinkedList
            for (LeaveRequest req : leaveHistory) {
                if (req.getRequestId().equalsIgnoreCase(reqId)) {
                    targetReq = req;
                    break;
                }
            }

            if (targetReq == null) {
                throw new Exception("Error: Leave Request ID '" + reqId + "' not found!");
            }

            if (!targetReq.getStatus().equals("Pending")) {
                throw new Exception("Error: Request is already " + targetReq.getStatus() + "!");
            }

            Employee emp = employeeMap.get(targetReq.getEmployeeId());
            if (emp == null) {
                throw new Exception("Error: Associated Employee no longer exists!");
            }

            if (targetReq.getDays() > emp.getLeaveBalance()) {
                throw new Exception("Error: Cannot approve. Insufficient balance (" + emp.getLeaveBalance() + " days remaining).");
            }

            // Reduce leave balance
            emp.setLeaveBalance(emp.getLeaveBalance() - targetReq.getDays());
            targetReq.setStatus("Approved");

            displayArea.setText("SUCCESS: Leave Request Approved!\n\n" + targetReq.toString() +
                               "\nUpdated Employee Leave Balance: " + emp.getLeaveBalance() + " days.");
            clearInputFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Approve Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Operation 8: Reject leave
     */
    private void rejectLeave() {
        try {
            String reqId = txtRequestId.getText().trim();
            if (reqId.isEmpty()) {
                throw new Exception("Validation Error: Please enter Request ID to reject!");
            }

            LeaveRequest targetReq = null;

            for (LeaveRequest req : leaveHistory) {
                if (req.getRequestId().equalsIgnoreCase(reqId)) {
                    targetReq = req;
                    break;
                }
            }

            if (targetReq == null) {
                throw new Exception("Error: Leave Request ID '" + reqId + "' not found!");
            }

            targetReq.setStatus("Rejected");

            displayArea.setText("SUCCESS: Leave Request Rejected!\n\n" + targetReq.toString());
            clearInputFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Reject Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Operation 9: View Leave Balance for an employee
     */
    private void viewLeaveBalance() {
        try {
            String empId = txtEmpId.getText().trim();
            if (empId.isEmpty()) {
                throw new Exception("Validation Error: Please enter Employee ID to check balance!");
            }

            if (!employeeMap.containsKey(empId)) {
                throw new Exception("Error: Employee ID '" + empId + "' not found!");
            }

            Employee emp = employeeMap.get(empId);
            displayArea.setText("========================================================================\n" +
                               "                         LEAVE BALANCE SUMMARY                          \n" +
                               "========================================================================\n" +
                               "Employee ID   : " + emp.getEmployeeId() + "\n" +
                               "Name          : " + emp.getName() + "\n" +
                               "Department    : " + emp.getDepartment() + "\n" +
                               "Leave Balance : " + emp.getLeaveBalance() + " days available\n\n" +
                               "System Leave Types Offered:\n" +
                               " - " + leaveTypesArray[0].toString() + "\n" +
                               " - " + leaveTypesArray[1].toString() + "\n" +
                               " - " + leaveTypesArray[2].toString() + "\n");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Balance Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Operation 10: Generate Comprehensive Leave Report
     */
    private void generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================================================================\n");
        sb.append("                               EMPLOYEE LEAVE REPORT                                     \n");
        sb.append("=========================================================================================\n");
        sb.append(String.format("%-8s | %-15s | %-18s | %-13s | %-4s | %-9s | %-10s\n",
                "Emp ID", "Name", "Department", "Leave Type", "Days", "Status", "Rem. Bal"));
        sb.append("-----------------------------------------------------------------------------------------\n");

        if (leaveHistory.isEmpty()) {
            sb.append("No leave applications recorded in history.\n");
        } else {
            for (LeaveRequest req : leaveHistory) {
                Employee emp = employeeMap.get(req.getEmployeeId());
                String empName = (emp != null) ? emp.getName() : "Unknown";
                String dept = (emp != null) ? emp.getDepartment() : "N/A";
                int remBal = (emp != null) ? emp.getLeaveBalance() : 0;

                sb.append(String.format("%-8s | %-15s | %-18s | %-13s | %-4d | %-9s | %-10d\n",
                        req.getEmployeeId(), empName, dept, req.getLeaveType(), req.getDays(), req.getStatus(), remBal));
            }
        }
        sb.append("=========================================================================================\n");
        displayArea.setText(sb.toString());
    }

    /**
     * Helper method to clear GUI text inputs
     */
    private void clearInputFields() {
        txtEmpId.setText("");
        txtEmpName.setText("");
        txtEmpEmail.setText("");
        txtDepartment.setText("");
        txtLeaveDays.setText("");
        txtReason.setText("");
        txtRequestId.setText("");
    }

    /**
     * Main method to launch the Swing application
     */
    public static void main(String[] args) {
        // Set cross-platform look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        // Run Swing GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}
