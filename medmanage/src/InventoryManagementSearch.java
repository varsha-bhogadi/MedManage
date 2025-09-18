import javax.swing.*;

import java.sql.*;

public class InventoryManagementSearch {
    // Image sources
    ImageIcon enterIcon = new ImageIcon(InventoryManagementSearch.class.getResource("Enter.png"));
    ImageIcon checkIcon =new ImageIcon(InventoryManagementSearch.class.getResource("Check.png"));
    ImageIcon errorIcon = new ImageIcon(InventoryManagementSearch.class.getResource("Error.png"));

    // Constructor
    public InventoryManagementSearch() {
        Connection connection = null;

        // Prompt for StockID or StockName
        String[] options = {"Stock ID", "Stock Name"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Would you like to search by Stock ID or Stock Name?",
                "Search Option", 
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                enterIcon,
                options,
                options[0]
        );

        if (choice == -1) {
            return; // User cancelled
        }

        while (true) {
            String searchInput = (String) JOptionPane.showInputDialog(
                    null,
                    choice == 0 ? "Enter Stock ID" : "Enter Stock Name",
                    "Enter",
                    JOptionPane.PLAIN_MESSAGE,
                    enterIcon,
                    null,
                    ""
            );

            if (searchInput == null) {
                return; // User cancelled
            }

            try {
                connection = DriverManager.getConnection(Global.url, Global.userName, Global.password);
                if (connection != null) {
                    PreparedStatement pst;
                    if (choice == 0) {
                        pst = connection.prepareStatement("SELECT * FROM Inventory WHERE StockID = ?");
                        pst.setString(1, searchInput);
                    } else {
                        pst = connection.prepareStatement("SELECT * FROM Inventory WHERE StockName = ?");
                        pst.setString(1, searchInput);
                    }

                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        String stockDetails = String.format(
                            "StockID: %s\nStock Name: %s\nQuantity: %s\nPrice: %s\nExpiry Date: %s",
                            rs.getString("StockID"),
                            rs.getString("StockName"),
                            rs.getString("StockQuantity"),
                            rs.getString("StockPrice"),
                            rs.getString("ExpiryDate")
                        );
                        JOptionPane.showMessageDialog(null, stockDetails, "Stock Details", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Stock not found", "Error", JOptionPane.ERROR_MESSAGE, errorIcon);
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
        new InventoryManagementSearch();
    }
}
