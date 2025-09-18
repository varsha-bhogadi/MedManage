import javax.swing.*;
import java.sql.*;

public class InventoryManagementRemove {

    // Image sources

    ImageIcon enterIcon = new ImageIcon(InventoryManagementRemove.class.getResource("Enter.png"));
    ImageIcon checkIcon = new ImageIcon(InventoryManagementRemove.class.getResource("Check.png"));
    ImageIcon errorIcon = new ImageIcon(InventoryManagementRemove.class.getResource("Error.png"));

    // Constructor
    public InventoryManagementRemove() {
        String identifier = null;
        Connection connection = null;

        String[] options = {"StockID", "StockName"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Remove by StockID or StockName?",
                "Choose Identifier",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                enterIcon,
                options,
                options[0]
        );

        if (choice == -1) {
            return; 
        }

        String identifierType = options[choice];

        while (true) {
            identifier = (String) JOptionPane.showInputDialog(
                null,
                "Enter " + identifierType,
                "Enter",
                JOptionPane.PLAIN_MESSAGE,
                enterIcon,
                null,
                ""
            );

            if (identifier == null) {
                return;
            }

            try {
                connection = DriverManager.getConnection(Global.url, Global.userName, Global.password);
                if (connection != null) {
                    System.out.println("Connection established successfully.");

                    String sql;
                    if (identifierType.equals("StockID")) {
                        sql = "DELETE FROM Inventory WHERE StockID=?";
                    } else {
                        sql = "DELETE FROM Inventory WHERE StockName=?";
                    }

                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.setString(1, identifier);

                    int rowsAffected = pst.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Inventory item removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE, checkIcon);
                    } else {
                        JOptionPane.showMessageDialog(null, "Inventory item not found", "Error", JOptionPane.ERROR_MESSAGE, errorIcon);
                    }
                    break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, errorIcon);
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
        new InventoryManagementRemove();
    }
}
