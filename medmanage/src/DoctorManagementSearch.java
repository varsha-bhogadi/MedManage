import javax.swing.*;

import java.sql.*;

public class DoctorManagementSearch {
    // Image sources
    ImageIcon enterIcon = new ImageIcon(DoctorManagementSearch.class.getResource("Enter.png"));
    ImageIcon checkIcon = new ImageIcon(DoctorManagementSearch.class.getResource("Check.png"));
    ImageIcon errorIcon = new ImageIcon(DoctorManagementSearch.class.getResource("Error.png"));

    // Constructor
    public DoctorManagementSearch() {
        String id = null;
        Connection connection = null;

        while (true) {
            id = (String) JOptionPane.showInputDialog(
                null,
                "Enter DoctorID",
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
                    String sql = "SELECT * FROM Doctors WHERE DoctorID=?";
                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.setString(1, id);
                    
                    ResultSet rs = pst.executeQuery();
                    
                    if (rs.next()) {
                        String doctorDetails = String.format(
                            "DoctorID: %s\nFirst Name: %s\nLast Name: %s\nSpecialization: %s\nGender: %s\nContact Number: %s\nEmail: %s\nAddress: %s\nState: %s",
                            rs.getString("DoctorID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("Specialization"),
                            rs.getString("Gender"),
                            rs.getString("ContactNumber"),
                            rs.getString("Email"),
                            rs.getString("Address"),
                            rs.getString("State")
                        );
                        JOptionPane.showMessageDialog(null, doctorDetails, "Doctor Details", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Doctor not found", "Error", JOptionPane.ERROR_MESSAGE, errorIcon);
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
        new DoctorManagementSearch();
    }
}
