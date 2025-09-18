import javax.swing.*;
import java.sql.*;

public class PatientManagementRemove {

    // Image sources
    ImageIcon enterIcon = new ImageIcon(PatientManagementRemove.class.getResource("Enter.png"));
    ImageIcon checkIcon = new ImageIcon(PatientManagementAdd.class.getResource("Check.png"));
    ImageIcon errorIcon = new ImageIcon(PatientManagementAdd.class.getResource("Error.png"));

    // Constructor
    public PatientManagementRemove() {
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
                    // Confirm deletion
                    int response = JOptionPane.showConfirmDialog(
                        null,
                        "This will remove the patient and all related appointments and payments. Do you want to continue?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );

                    if (response == JOptionPane.NO_OPTION) {
                        return;
                    }

                    // Delete patient and cascade
                    String sql = "DELETE FROM Patients WHERE PatientID=?";
                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.setString(1, id);
                    
                    int rowsAffected = pst.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Patient information removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE, checkIcon);
                    } else {
                        JOptionPane.showMessageDialog(null, "Patient not found", "Error", JOptionPane.ERROR_MESSAGE, errorIcon);
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
        new PatientManagementRemove();
    }
}
