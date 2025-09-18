import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StaffManagementUpdate {

    // Fields
    private JFrame staffUpdate;
    private JTextField firstNameField, lastNameField, roleField, contactNumberField, emailField, addressField, stateField;
    private JComboBox<String> genderField;
    String staffID, firstName, lastName, role, gender, contactNumber, email, address, state;
            // Image sources
            ImageIcon logo = new ImageIcon(StaffManagementUpdate.class.getResource("MedManageLogo.png"));
            ImageIcon errorIcon = new ImageIcon(StaffManagementUpdate.class.getResource("Error.png"));
            ImageIcon checkIcon = new ImageIcon(StaffManagementUpdate.class.getResource("Check.png"));
            ImageIcon enterIcon = new ImageIcon(StaffManagementUpdate.class.getResource("Enter.png"));

    // Constructor
    public StaffManagementUpdate() {

        while (true) {
            staffID = (String) JOptionPane.showInputDialog(
                null,
                "Enter StaffID",
                "Enter",
                JOptionPane.PLAIN_MESSAGE,
                enterIcon,
                null,
                ""
            );

            if (staffID == null) {
                return;
            }

            try {
                Connection con = DriverManager.getConnection(Global.url, Global.userName, Global.password);
                PreparedStatement pst = con.prepareStatement("SELECT * FROM Staff WHERE StaffID = ?");
                pst.setString(1, staffID);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    firstName = rs.getString("FirstName");
                    lastName = rs.getString("LastName");
                    role = rs.getString("Role");
                    gender = rs.getString("Gender");
                    contactNumber = rs.getString("ContactNumber");
                    email = rs.getString("Email");
                    address = rs.getString("Address");
                    state = rs.getString("State");
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Staff ID not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Oops unexpected error restart.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Frame creation
        staffUpdate = new JFrame();
        staffUpdate.setTitle("Staff Update Form");
        staffUpdate.setSize(500, 650);
        staffUpdate.setLocationRelativeTo(null);
        staffUpdate.setLayout(null);
        staffUpdate.setIconImage(logo.getImage());
        staffUpdate.setResizable(false);
        staffUpdate.getContentPane().setBackground(new Color(230, 247, 255));

        // Labels and text fields
        Font f1 = new Font("Times New Roman", Font.BOLD, 15);

        JLabel firstNameLabel = new JLabel("First Name: ");
        firstNameLabel.setFont(f1);
        firstNameLabel.setBounds(50, 50, 150, 30);
        staffUpdate.add(firstNameLabel);
        firstNameField = new JTextField();
        firstNameField.setText(firstName);
        firstNameField.setBounds(250, 50, 200, 30);
        staffUpdate.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name: ");
        lastNameLabel.setFont(f1);
        lastNameLabel.setBounds(50, 100, 150, 30);
        staffUpdate.add(lastNameLabel);
        lastNameField = new JTextField();
        lastNameField.setText(lastName);
        lastNameField.setBounds(250, 100, 200, 30);
        staffUpdate.add(lastNameField);

        JLabel roleLabel = new JLabel("Role: ");
        roleLabel.setFont(f1);
        roleLabel.setBounds(50, 150, 150, 30);
        staffUpdate.add(roleLabel);
        roleField = new JTextField();
        roleField.setText(role);
        roleField.setBounds(250, 150, 200, 30);
        staffUpdate.add(roleField);

        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setFont(f1);
        genderLabel.setBounds(50, 200, 150, 30);
        staffUpdate.add(genderLabel);
        genderField = new JComboBox<>(new String[]{"Select Gender", "Male", "Female", "Other"});
        genderField.setSelectedItem(gender);
        genderField.setBounds(250, 200, 200, 30);
        staffUpdate.add(genderField);

        JLabel contactNumberLabel = new JLabel("Contact Number: ");
        contactNumberLabel.setFont(f1);
        contactNumberLabel.setBounds(50, 250, 150, 30);
        staffUpdate.add(contactNumberLabel);
        contactNumberField = new JTextField();
        contactNumberField.setText(contactNumber);
        contactNumberField.setBounds(250, 250, 200, 30);
        staffUpdate.add(contactNumberField);

        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setFont(f1);
        emailLabel.setBounds(50, 300, 150, 30);
        staffUpdate.add(emailLabel);
        emailField = new JTextField();
        emailField.setText(email);
        emailField.setBounds(250, 300, 200, 30);
        staffUpdate.add(emailField);

        JLabel addressLabel = new JLabel("Address: ");
        addressLabel.setFont(f1);
        addressLabel.setBounds(50, 350, 150, 30);
        staffUpdate.add(addressLabel);
        addressField = new JTextField();
        addressField.setText(address);
        addressField.setBounds(250, 350, 200, 30);
        staffUpdate.add(addressField);

        JLabel stateLabel = new JLabel("State: ");
        stateLabel.setFont(f1);
        stateLabel.setBounds(50, 400, 150, 30);
        staffUpdate.add(stateLabel);
        stateField = new JTextField();
        stateField.setText(state);
        stateField.setBounds(250, 400, 200, 30);
        staffUpdate.add(stateField);

        // Button
        JButton updateButton = new JButton("Update");
        updateButton.setFont(f1);
        updateButton.setBackground(Color.BLACK);
        updateButton.setForeground(Color.white);
        updateButton.setBounds(150, 500, 200, 50);
        updateButton.setFocusPainted(false);
        staffUpdate.add(updateButton);

        // Action listener for the update button
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    // Update database
                    updateDatabase(firstNameField.getText(), lastNameField.getText(), roleField.getText(),
                            genderField.getSelectedItem().toString(), contactNumberField.getText(), emailField.getText(),
                            addressField.getText(), stateField.getText(), staffID);
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
        addEnterKeyListener(stateField, updateButton);

        // Visibility and exiting frame
        staffUpdate.setVisible(true);
        staffUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

    // Update data into the database
    private void updateDatabase(String firstName, String lastName, String role, String gender, String contactNumber,
                                String email, String address, String state, String staffID) {
        String updateSQL = "UPDATE Staff SET FirstName = ?, LastName = ?, Role = ?, Gender = ?, ContactNumber = ?, Email = ?, Address = ?, State = ? WHERE StaffID = ?;";

        try (Connection connection = DriverManager.getConnection(Global.url, Global.userName, Global.password);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, role);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, contactNumber);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, address);
            preparedStatement.setString(8, state);
            preparedStatement.setString(9, staffID);

            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(null, "Staff information updated successfully! Staff ID: " + staffID, "Success", JOptionPane.INFORMATION_MESSAGE, checkIcon);
                staffUpdate.dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error updating data in the database.", "Database Error", JOptionPane.ERROR_MESSAGE, errorIcon);
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

    // Main method
    public static void main(String[] args) {
        // Set look and feel to system default (if needed)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Create and display the update form
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StaffManagementUpdate();
            }
        });
    }
}
