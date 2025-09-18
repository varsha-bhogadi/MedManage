import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class InventoryManagementUpdate {

    // Fields
    private JFrame stockUpdate;
    private JTextField stockNameField, stockQuantityField, stockPriceField, expiryDateField;
    String stockID, stockName, stockQuantity, stockPrice, expiryDate;

            // Image sources
            ImageIcon logo = new ImageIcon(InventoryManagementUpdate.class.getResource("MedManageLogo.png"));
            ImageIcon errorIcon = new ImageIcon(InventoryManagementUpdate.class.getResource("Error.png"));
            ImageIcon checkIcon = new ImageIcon(InventoryManagementUpdate.class.getResource("Check.png"));
            ImageIcon enterIcon = new ImageIcon(InventoryManagementUpdate.class.getResource("Enter.png"));

    // Constructor
    public InventoryManagementUpdate() {

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
                Connection con = DriverManager.getConnection(Global.url, Global.userName, Global.password);
                PreparedStatement pst;
                if (choice == 0) {
                    pst = con.prepareStatement("SELECT * FROM Inventory WHERE StockID = ?");
                    pst.setString(1, searchInput);
                } else {
                    pst = con.prepareStatement("SELECT * FROM Inventory WHERE StockName = ?");
                    pst.setString(1, searchInput);
                }
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    stockID = rs.getString("StockID");
                    stockName = rs.getString("StockName");
                    stockQuantity = rs.getString("StockQuantity");
                    stockPrice = rs.getString("StockPrice");
                    expiryDate = rs.getString("ExpiryDate");
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Stock not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Unexpected error. Please restart.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Frame creation
        stockUpdate = new JFrame();
        stockUpdate.setTitle("Stock Update Form");
        stockUpdate.setSize(500, 450);
        stockUpdate.setLocationRelativeTo(null);
        stockUpdate.setLayout(null);
        stockUpdate.setIconImage(logo.getImage());
        stockUpdate.setResizable(false);
        stockUpdate.getContentPane().setBackground(new Color(230, 247, 255));

        // Labels and text fields
        Font f1 = new Font("Times New Roman", Font.BOLD, 15);

        JLabel stockNameLabel = new JLabel("Stock Name: ");
        stockNameLabel.setFont(f1);
        stockNameLabel.setBounds(50, 50, 150, 30);
        stockUpdate.add(stockNameLabel);
        stockNameField = new JTextField();
        stockNameField.setText(stockName);
        stockNameField.setBounds(250, 50, 200, 30);
        stockUpdate.add(stockNameField);

        JLabel stockQuantityLabel = new JLabel("Quantity: ");
        stockQuantityLabel.setFont(f1);
        stockQuantityLabel.setBounds(50, 100, 150, 30);
        stockUpdate.add(stockQuantityLabel);
        stockQuantityField = new JTextField();
        stockQuantityField.setText(stockQuantity);
        stockQuantityField.setBounds(250, 100, 200, 30);
        stockUpdate.add(stockQuantityField);

        JLabel stockPriceLabel = new JLabel("Price: ");
        stockPriceLabel.setFont(f1);
        stockPriceLabel.setBounds(50, 150, 150, 30);
        stockUpdate.add(stockPriceLabel);
        stockPriceField = new JTextField();
        stockPriceField.setText(stockPrice);
        stockPriceField.setBounds(250, 150, 200, 30);
        stockUpdate.add(stockPriceField);

        JLabel expiryDateLabel = new JLabel("Expiry Date: ");
        expiryDateLabel.setFont(f1);
        expiryDateLabel.setBounds(50, 200, 150, 30);
        stockUpdate.add(expiryDateLabel);
        expiryDateField = new JTextField();
        expiryDateField.setText(expiryDate);
        expiryDateField.setBounds(250, 200, 200, 30);
        stockUpdate.add(expiryDateField);

        // Button
        JButton updateButton = new JButton("Update");
        updateButton.setFont(f1);
        updateButton.setBackground(Color.BLACK);
        updateButton.setForeground(Color.white);
        updateButton.setBounds(150, 300, 200, 50);
        updateButton.setFocusPainted(false);
        stockUpdate.add(updateButton);

        // Action listener for the update button
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    // Update database
                    updateDatabase(stockNameField.getText(), stockQuantityField.getText(), stockPriceField.getText(),
                            expiryDateField.getText(), stockID);
                }
            }
        });

        // Add KeyListener to navigate with Enter key
        addEnterKeyListener(stockNameField, stockQuantityField);
        addEnterKeyListener(stockQuantityField, stockPriceField);
        addEnterKeyListener(stockPriceField, expiryDateField);
        addEnterKeyListener(expiryDateField, updateButton);

        // Visibility and exiting frame
        stockUpdate.setVisible(true);
        stockUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Validate input fields
    private boolean validateInput() {
        if (stockNameField.getText().trim().isEmpty() ||
            stockQuantityField.getText().trim().isEmpty() ||
            stockPriceField.getText().trim().isEmpty() ||
            expiryDateField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all required fields.", "Input Error", JOptionPane.PLAIN_MESSAGE, errorIcon);
            return false;
        }
        try {
            Integer.parseInt(stockQuantityField.getText()); // Validate quantity
            Double.parseDouble(stockPriceField.getText());   // Validate price
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Quantity and Price must be valid numbers.", "Input Error", JOptionPane.PLAIN_MESSAGE, errorIcon);
            return false;
        }
        return true;
    }

    // Update data into the database
    private void updateDatabase(String stockName, String stockQuantity, String stockPrice, String expiryDate, String stockID) {
        String updateSQL = "UPDATE Inventory SET StockName = ?, StockQuantity = ?, StockPrice = ?, ExpiryDate = ? WHERE StockID = ?;";

        try (Connection connection = DriverManager.getConnection(Global.url, Global.userName, Global.password);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, stockName);
            preparedStatement.setString(2, stockQuantity);
            preparedStatement.setString(3, stockPrice);
            preparedStatement.setString(4, expiryDate);
            preparedStatement.setString(5, stockID);

            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(null, "Stock information updated successfully! Stock ID: " + stockID, "Success", JOptionPane.INFORMATION_MESSAGE, checkIcon);
                stockUpdate.dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error updating data in the database.", "Database Error", JOptionPane.ERROR_MESSAGE, errorIcon);
        }
    }

    // Method to add Enter Key Listener
    private void addEnterKeyListener(JTextField currentField, JComponent nextComponent) {
        currentField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    nextComponent.requestFocusInWindow();
                }
            }
        });
    }

    public static void main(String[] args) {
        new InventoryManagementUpdate();
    }
}
