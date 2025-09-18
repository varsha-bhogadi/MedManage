import javax.swing.*;
import java.sql.*;

public class StaffManagementRemove {

    // Image sources
    ImageIcon enterIcon = new ImageIcon(StaffManagementRemove.class.getResource("Enter.png"));
    ImageIcon checkIcon = new ImageIcon(StaffManagementRemove.class.getResource("Check.png"));
    ImageIcon errorIcon = new ImageIcon(StaffManagementRemove.class.getResource("Error.png"));

    // Constructor
    public StaffManagementRemove() {
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
                    System.out.println("Connection established successfully.");

                    String sql = "DELETE FROM Staff WHERE StaffID=?";
                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.setString(1, id);

                    int rowsAffected = pst.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Staff information Removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE,checkIcon);
                    } else {
                        JOptionPane.showMessageDialog(null, "Staff Not found", "Error", JOptionPane.ERROR_MESSAGE, errorIcon);
                    }
                    break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        new StaffManagementRemove();
    }
}
