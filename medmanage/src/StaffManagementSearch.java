import javax.swing.*;
import java.sql.*;

public class StaffManagementSearch {
    // Image sources
    ImageIcon enterIcon = new ImageIcon(StaffManagementSearch.class.getResource("Enter.png"));
    ImageIcon checkIcon = new ImageIcon(StaffManagementSearch.class.getResource("Check.png"));
    ImageIcon errorIcon = new ImageIcon(StaffManagementSearch.class.getResource("Error.png"));

    // Constructor
    public StaffManagementSearch() {
        String id = null;
        Connection connection = null;

        while (true) {
            id = (String) JOptionPane.showInputDialog(
                null,
                "Enter StaffID",
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
                    String sql = "SELECT * FROM Staff WHERE StaffID=?";
                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.setString(1, id);
                    
                    ResultSet rs = pst.executeQuery();
                    
                    if (rs.next()) {
                        String staffDetails = String.format(
                            "StaffID: %s\nFirst Name: %s\nLast Name: %s\nRole: %s\nGender: %s\nContact Number: %s\nEmail: %s\nAddress: %s\nState: %s",
                            rs.getString("StaffID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("Role"),
                            rs.getString("Gender"),
                            rs.getString("ContactNumber"),
                            rs.getString("Email"),
                            rs.getString("Address"),
                            rs.getString("State")
                        );
                        JOptionPane.showMessageDialog(null, staffDetails, "Staff Details", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Staff not found", "Error", JOptionPane.ERROR_MESSAGE, errorIcon);
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
        new StaffManagementSearch();
    }
}
