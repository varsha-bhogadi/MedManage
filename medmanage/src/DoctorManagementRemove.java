import javax.swing.*;

import java.sql.*;

public class DoctorManagementRemove {

    // Image sources
    ImageIcon enterIcon = new ImageIcon(DoctorManagementRemove.class.getResource("Enter.png"));
    ImageIcon checkIcon = new ImageIcon(DoctorManagementRemove.class.getResource("Check.png"));
    ImageIcon errorIcon = new ImageIcon(DoctorManagementRemove.class.getResource("Error.png"));

    // Constructor
    public DoctorManagementRemove() {
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
                    // Confirm deletion
                    int response = JOptionPane.showConfirmDialog(
                        null,
                        "This will remove the doctor and all related appointments. Do you want to continue?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );

                    if (response == JOptionPane.NO_OPTION) {
                        return;
                    }

                    // Delete doctor and cascade
                    String sql = "DELETE FROM Doctors WHERE DoctorID=?";
                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.setString(1, id);
                    
                    int rowsAffected = pst.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Doctor information removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE, checkIcon);
                    } else {
                        JOptionPane.showMessageDialog(null, "Doctor not found", "Error", JOptionPane.ERROR_MESSAGE, errorIcon);
                    }
                    break;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, errorIcon);
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new DoctorManagementRemove();
    }
}
