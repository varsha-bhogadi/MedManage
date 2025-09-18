import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StaffManagementAdd {

    // Fields
    private JFrame staffAdd;
    public static JTextField firstNameField, lastNameField, roleField, contactNumberField, emailField, addressField, stateField;
    public JComboBox<String> genderField;
    public static int staffID;

            // Image sources
            ImageIcon logo = new ImageIcon(StaffManagementAdd.class.getResource("MedManageLogo.png"));
            ImageIcon errorIcon = new ImageIcon(StaffManagementAdd.class.getResource("Error.png"));

    // Constructor
    public StaffManagementAdd() {

        
        // Frame creation
        staffAdd = new JFrame();
        staffAdd.setTitle("Staff Form");
        staffAdd.setSize(500, 650);
        staffAdd.setLocationRelativeTo(null);
        staffAdd.setLayout(null); 
        staffAdd.setIconImage(logo.getImage());
        staffAdd.setResizable(false);
        staffAdd.getContentPane().setBackground(new Color(230, 247, 255));

        // Labels and text fields
        Font f1 = new Font("Times New Roman", Font.BOLD, 15);

        JLabel firstNameLabel = new JLabel("First Name: ");
        firstNameLabel.setFont(f1);
        firstNameLabel.setBounds(50, 50, 150, 30); 
        staffAdd.add(firstNameLabel);
        firstNameField = new JTextField();
        firstNameField.setBounds(250, 50, 200, 30); 
        staffAdd.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name: ");
        lastNameLabel.setFont(f1);
        lastNameLabel.setBounds(50, 100, 150, 30); 
        staffAdd.add(lastNameLabel);
        lastNameField = new JTextField();
        lastNameField.setBounds(250, 100, 200, 30); 
        staffAdd.add(lastNameField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(f1);
        roleLabel.setBounds(50, 150, 150, 30); 
        staffAdd.add(roleLabel);
        roleField = new JTextField();
        roleField.setBounds(250, 150, 200, 30); 
        staffAdd.add(roleField);

        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setFont(f1);
        genderLabel.setBounds(50, 200, 150, 30); 
        staffAdd.add(genderLabel);
        genderField = new JComboBox<>(new String[]{"Select Gender", "Male", "Female", "Other"});
        genderField.setBounds(250, 200, 200, 30); 
        staffAdd.add(genderField);
        genderField.setSelectedIndex(0);

        JLabel contactNumberLabel = new JLabel("Contact Number: ");
        contactNumberLabel.setFont(f1);
        contactNumberLabel.setBounds(50, 250, 150, 30); 
        staffAdd.add(contactNumberLabel);
        contactNumberField = new JTextField();
        contactNumberField.setBounds(250, 250, 200, 30); 
        staffAdd.add(contactNumberField);

        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setFont(f1);
        emailLabel.setBounds(50, 300, 150, 30); 
        staffAdd.add(emailLabel);
        emailField = new JTextField();
        emailField.setBounds(250, 300, 200, 30); 
        staffAdd.add(emailField);

        JLabel addressLabel = new JLabel("Address: ");
        addressLabel.setFont(f1);
        addressLabel.setBounds(50, 350, 150, 30); 
        staffAdd.add(addressLabel);
        addressField = new JTextField();
        addressField.setBounds(250, 350, 200, 30); 
        staffAdd.add(addressField);

        JLabel stateLabel = new JLabel("State: ");
        stateLabel.setFont(f1);
        stateLabel.setBounds(50, 400, 150, 30); 
        staffAdd.add(stateLabel);
        stateField = new JTextField();
        stateField.setBounds(250, 400, 200, 30); 
        staffAdd.add(stateField);

        // Button
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(f1);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.white);
        submitButton.setBounds(150, 480, 200, 50);
        submitButton.setFocusPainted(false);
        staffAdd.add(submitButton);

        // Action listener for the submit button
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    // Insert into database
                    insertIntoDatabase(firstNameField.getText(), lastNameField.getText(), roleField.getText(),
                            genderField.getSelectedItem().toString(), contactNumberField.getText(),
                            emailField.getText(), addressField.getText(), stateField.getText());
                }
            }
        });

        // Add KeyListener to navigate with Enter key
        addEnterKeyListener(firstNameField, lastNameField);
        addEnterKeyListener(lastNameField, roleField);
        addEnterKeyListener(roleField, genderField);
        addComboBoxKeyListener(genderField, contactNumberField);
        addEnterKeyListener(contactNumberField, emailField);
        addEnterKeyListener(emailField, addressField);
        addEnterKeyListener(addressField, stateField);
        addEnterKeyListener(stateField, submitButton);

        // Set initial focus
        firstNameField.requestFocusInWindow();

        // Visibility and exiting frame
        staffAdd.setVisible(true);
        staffAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Validate input fields
    private boolean validateInput() {
        if (firstNameField.getText().trim().isEmpty() ||
            lastNameField.getText().trim().isEmpty() ||
            roleField.getText().trim().isEmpty() ||
            genderField.getSelectedIndex() == 0 || 
            contactNumberField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty() ||
            addressField.getText().trim().isEmpty() ||
            stateField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all required fields.", "Input Error", JOptionPane.PLAIN_MESSAGE, errorIcon);
            return false;
        }
        return true;
    }

    // Insert data into the database
    private void insertIntoDatabase(String firstName, String lastName, String role, String gender,
                                    String contactNumber, String email, String address, String state) {
        String insertSQL = "INSERT INTO Staff (FirstName, LastName, Role, Gender, ContactNumber, Email, Address, State) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(Global.url, Global.userName, Global.password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, role);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, contactNumber);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, address);
            preparedStatement.setString(8, state);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        staffID = generatedKeys.getInt(1);
                        new StaffManagementRegistration();
                        staffAdd.dispose();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error inserting data into the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to add KeyListener to text fields
    private void addEnterKeyListener(JTextField currentField, JComponent nextComponent) {
        currentField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    nextComponent.requestFocusInWindow();
                }
            }
        });
    }

    // Add key listener for Enter key navigation for JComboBox
    private void addComboBoxKeyListener(JComboBox<String> currentComboBox, JComponent nextComponent) {
        currentComboBox.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    nextComponent.requestFocusInWindow();
                }
            }
        });
    }

    public static void main(String[] args) {
        new StaffManagementAdd();
    }
}
