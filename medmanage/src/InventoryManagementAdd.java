import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class InventoryManagementAdd {

    // Fields
    public JFrame inventoryAdd;
    public static int stockID;
    public static JTextField stockNameField, stockQuantityField, stockPriceField, expiryDateField;

    // Image sources
    ImageIcon logo = new ImageIcon(InventoryManagementAdd.class.getResource("MedManageLogo.png"));
    ImageIcon errorIcon = new ImageIcon(InventoryManagementAdd.class.getResource("Error.png"));
    ImageIcon checkIcon = new ImageIcon(InventoryManagementAdd.class.getResource("Check.png"));

    // Constructor
    public InventoryManagementAdd() {


        // Frame creation
        inventoryAdd = new JFrame();
        inventoryAdd.setTitle("Inventory Form");
        inventoryAdd.setSize(500, 500);
        inventoryAdd.setLocationRelativeTo(null);
        inventoryAdd.setLayout(null);
        inventoryAdd.setIconImage(logo.getImage());
        inventoryAdd.setResizable(false);
        inventoryAdd.getContentPane().setBackground(new Color(230, 247, 255));

        // Labels and text fields
        Font f1 = new Font("Times New Roman", Font.BOLD, 15);

        JLabel stockNameLabel = new JLabel("Stock Name: ");
        stockNameLabel.setFont(f1);
        stockNameLabel.setBounds(50, 50, 150, 30);
        inventoryAdd.add(stockNameLabel);
        stockNameField = new JTextField();
        stockNameField.setBounds(250, 50, 200, 30);
        inventoryAdd.add(stockNameField);

        JLabel stockQuantityLabel = new JLabel("Quantity: ");
        stockQuantityLabel.setFont(f1);
        stockQuantityLabel.setBounds(50, 100, 150, 30);
        inventoryAdd.add(stockQuantityLabel);
        stockQuantityField = new JTextField();
        stockQuantityField.setBounds(250, 100, 200, 30);
        inventoryAdd.add(stockQuantityField);

        JLabel stockPriceLabel = new JLabel("Price: ");
        stockPriceLabel.setFont(f1);
        stockPriceLabel.setBounds(50, 150, 150, 30);
        inventoryAdd.add(stockPriceLabel);
        stockPriceField = new JTextField();
        stockPriceField.setBounds(250, 150, 200, 30);
        inventoryAdd.add(stockPriceField);

        JLabel expiryDateLabel = new JLabel("Expiry Date: ");
        expiryDateLabel.setFont(f1);
        expiryDateLabel.setBounds(50, 200, 150, 30);
        inventoryAdd.add(expiryDateLabel);
        expiryDateField = new JTextField();
        expiryDateField.setBounds(250, 200, 200, 30);
        inventoryAdd.add(expiryDateField);

        // Button
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.setBounds(150, 300, 200, 50);
        submitButton.setFocusPainted(false);
        inventoryAdd.add(submitButton);

        // Action listener for the submit button
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    insertIntoDatabase(stockNameField.getText(), 
                        Integer.parseInt(stockQuantityField.getText()), 
                        Double.parseDouble(stockPriceField.getText()), 
                        expiryDateField.getText());
                }
            }
        });

        // Adding key listeners for Enter key navigation
        addEnterKeyListener(stockNameField, stockQuantityField);
        addEnterKeyListener(stockQuantityField, stockPriceField);
        addEnterKeyListener(stockPriceField, expiryDateField);
        addEnterKeyListener(expiryDateField, submitButton);

        // Focus on the first text field
        stockNameField.requestFocusInWindow();

        // Visibility and exiting frame
        inventoryAdd.setVisible(true);
        inventoryAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
            Integer.parseInt(stockQuantityField.getText()); 
            Double.parseDouble(stockPriceField.getText());   
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Quantity and Price must be valid numbers.", "Input Error", JOptionPane.PLAIN_MESSAGE, errorIcon);
            return false;
        }
        return true;
    }

    private void insertIntoDatabase(String stockName, int quantity, double price, String expiryDate) {
        String insertSQL = "INSERT INTO Inventory (StockName, StockQuantity, StockPrice, ExpiryDate) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(Global.url, Global.userName, Global.password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, stockName);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, expiryDate);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        stockID = generatedKeys.getInt(1);
                        JOptionPane.showMessageDialog(null, "Stock added successfully!"+"Stock Id : "+stockID, "Success", JOptionPane.INFORMATION_MESSAGE, checkIcon);
                        inventoryAdd.dispose();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error inserting data into the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Add key listener for Enter key navigation for JTextField
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
        new InventoryManagementAdd();
    }
}
