import javax.swing.*;

import java.sql.*;

public class PatientManagementSearch {
    // Image sources
    ImageIcon enterIcon = new ImageIcon(PatientManagementSearch.class.getResource("Enter.png"));
    ImageIcon checkIcon = new ImageIcon(PatientManagementSearch.class.getResource("Check.png"));
    ImageIcon errorIcon = new ImageIcon(PatientManagementSearch.class.getResource("Error.png"));

    // Constructor
    public PatientManagementSearch() {
        String id = null;
        Connection connection = null;

        while (true) {
            id = (String) JOptionPane.showInputDialog(
                null,
                "Enter PatientID",
                "Enter",
                JOptionPane.PLAIN_MESSAGE,
                enterIcon,
                null,
                ""
            );
            
            if (id == null) {
                return; 
            }

            try {
                connection = DriverManager.getConnection(Global.url, Global.userName, Global.password);
                if (connection != null) {
                    String sql = "SELECT * FROM Patients WHERE PatientID=?";
                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.setString(1, id);
                    
                    ResultSet rs = pst.executeQuery();
                    
                    if (rs.next()) {
                        String patientDetails = String.format(
                            "PatientID: %s\nFirst Name: %s\nLast Name: %s\nDateOfBirth: %s\nGender: %s\nBloodPressure: %s\nContact Number: %s\nEmail: %s\nAddress: %s\nState: %s",
                            rs.getString("PatientID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("DateOfBirth"),
                            rs.getString("Gender"),
                            rs.getString("BloodPressure"),
                            rs.getString("ContactNumber"),
                            rs.getString("Email"),
                            rs.getString("Address"),
                            rs.getString("State")
                        );
                        JOptionPane.showMessageDialog(null, patientDetails, "Patient Details", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Patient not found", "Error", JOptionPane.ERROR_MESSAGE, errorIcon);
                    }
                    break;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, errorIcon);
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new PatientManagementSearch();
    }
}
