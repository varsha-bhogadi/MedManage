import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DoctorManagementUpdate {

    // Fields
    private JFrame doctorUpdate;
    private JTextField firstNameField, lastNameField, specializationField, contactNumberField, emailField, addressField, stateField;
    private JComboBox<String> genderField;
    String doctorID, firstName, lastName, specialization, gender, contactNumber, email, address, state;

    // Image sources
    ImageIcon logo = new ImageIcon(DoctorManagementUpdate.class.getResource("MedManageLogo.png"));
    ImageIcon errorIcon = new ImageIcon(DoctorManagementUpdate.class.getResource("Error.png"));
    ImageIcon checkIcon = new ImageIcon(DoctorManagementUpdate.class.getResource("Check.png"));
    ImageIcon enterIcon = new ImageIcon(DoctorManagementUpdate.class.getResource("Enter.png"));
    // Constructor
    public DoctorManagementUpdate() {

        while (true) {
            doctorID = (String) JOptionPane.showInputDialog(
                null,
                "Enter DoctorID",
                "Enter",
                JOptionPane.PLAIN_MESSAGE,
                enterIcon,
                null,
                ""
            );

            if (doctorID == null) {
                return;
            }

            try {
                Connection con = DriverManager.getConnection(Global.url, Global.userName, Global.password);
                PreparedStatement pst = con.prepareStatement("SELECT * FROM DOCTORS WHERE doctorID = ?");
                pst.setString(1, doctorID);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    firstName = rs.getString("FirstName");
                    lastName = rs.getString("LastName");
                    specialization = rs.getString("Specialization");
                    gender = rs.getString("Gender");
                    contactNumber = rs.getString("ContactNumber");
                    email = rs.getString("Email");
                    address = rs.getString("Address");
                    state = rs.getString("State");
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Doctor ID not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Oops unexpected error restart.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Frame creation
        doctorUpdate = new JFrame();
        doctorUpdate.setTitle("Doctor Update Form");
        doctorUpdate.setSize(500, 650);
        doctorUpdate.setLocationRelativeTo(null);
        doctorUpdate.setLayout(null);
        doctorUpdate.setIconImage(logo.getImage());
        doctorUpdate.setResizable(false);
        doctorUpdate.getContentPane().setBackground(new Color(230, 247, 255));

        // Labels and text fields
        Font f1 = new Font("Times New Roman", Font.BOLD, 15);

        JLabel firstNameLabel = new JLabel("First Name: ");
        firstNameLabel.setFont(f1);
        firstNameLabel.setBounds(50, 50, 150, 30);
        doctorUpdate.add(firstNameLabel);
        firstNameField = new JTextField();
        firstNameField.setText(firstName);
        firstNameField.setBounds(250, 50, 200, 30);
        doctorUpdate.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name: ");
        lastNameLabel.setFont(f1);
        lastNameLabel.setBounds(50, 100, 150, 30);
        doctorUpdate.add(lastNameLabel);
        lastNameField = new JTextField();
        lastNameField.setText(lastName);
        lastNameField.setBounds(250, 100, 200, 30);
        doctorUpdate.add(lastNameField);

        JLabel specializationLabel = new JLabel("Specialization: ");
        specializationLabel.setFont(f1);
        specializationLabel.setBounds(50, 150, 150, 30);
        doctorUpdate.add(specializationLabel);
        specializationField = new JTextField();
        specializationField.setText(specialization);
        specializationField.setBounds(250, 150, 200, 30);
        doctorUpdate.add(specializationField);

        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setFont(f1);
        genderLabel.setBounds(50, 200, 150, 30);
        doctorUpdate.add(genderLabel);
        genderField = new JComboBox<>(new String[]{"Select Gender", "Male", "Female", "Other"});
        genderField.setSelectedItem(gender);
        genderField.setBounds(250, 200, 200, 30);
        doctorUpdate.add(genderField);

        JLabel contactNumberLabel = new JLabel("Contact Number: ");
        contactNumberLabel.setFont(f1);
        contactNumberLabel.setBounds(50, 250, 150, 30);
        doctorUpdate.add(contactNumberLabel);
        contactNumberField = new JTextField();
        contactNumberField.setText(contactNumber);
        contactNumberField.setBounds(250, 250, 200, 30);
        doctorUpdate.add(contactNumberField);

        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setFont(f1);
        emailLabel.setBounds(50, 300, 150, 30);
        doctorUpdate.add(emailLabel);
        emailField = new JTextField();
        emailField.setText(email);
        emailField.setBounds(250, 300, 200, 30);
        doctorUpdate.add(emailField);

        JLabel addressLabel = new JLabel("Address: ");
        addressLabel.setFont(f1);
        addressLabel.setBounds(50, 350, 150, 30);
        doctorUpdate.add(addressLabel);
        addressField = new JTextField();
        addressField.setText(address);
        addressField.setBounds(250, 350, 200, 30);
        doctorUpdate.add(addressField);

        JLabel stateLabel = new JLabel("State: ");
        stateLabel.setFont(f1);
        stateLabel.setBounds(50, 400, 150, 30);
        doctorUpdate.add(stateLabel);
        stateField = new JTextField();
        stateField.setText(state);
        stateField.setBounds(250, 400, 200, 30);
        doctorUpdate.add(stateField);

        // Button
        JButton updateButton = new JButton("Update");
        updateButton.setFont(f1);
        updateButton.setBackground(Color.BLACK);
        updateButton.setForeground(Color.white);
        updateButton.setBounds(150, 500, 200, 50);
        updateButton.setFocusPainted(false);
        doctorUpdate.add(updateButton);

        // Action listener for the update button
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    // Update database
                    updateDatabase(firstNameField.getText(), lastNameField.getText(), specializationField.getText(),
                            genderField.getSelectedItem().toString(), contactNumberField.getText(), emailField.getText(),
                            addressField.getText(), stateField.getText(), doctorID);
                }
            }
        });

        // Add KeyListener to navigate with Enter key
        addEnterKeyListener(firstNameField, lastNameField);
        addEnterKeyListener(lastNameField, specializationField);
        addEnterKeyListener(specializationField, genderField);
        addComboBoxKeyListener(genderField, contactNumberField);
        addEnterKeyListener(contactNumberField, emailField);
        addEnterKeyListener(emailField, addressField);
        addEnterKeyListener(addressField, stateField);
        addEnterKeyListener(stateField, updateButton);

        // Visibility and exiting frame
        doctorUpdate.setVisible(true);
        doctorUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Validate input fields
    private boolean validateInput() {
        if (firstNameField.getText().trim().isEmpty() ||
            lastNameField.getText().trim().isEmpty() ||
            specializationField.getText().trim().isEmpty() ||
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
    private void updateDatabase(String firstName, String lastName, String specialization, String gender, String contactNumber,
                                String email, String address, String state, String doctorID) {
        String updateSQL = "UPDATE Doctors SET FirstName = ?, LastName = ?, Specialization = ?, Gender = ?, ContactNumber = ?, Email = ?, Address = ?, State = ? WHERE DoctorID = ?;";

        try (Connection connection = DriverManager.getConnection(Global.url, Global.userName, Global.password);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, specialization);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, contactNumber);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, address);
            preparedStatement.setString(8, state);
            preparedStatement.setString(9, doctorID);

            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(null, "Doctor information updated successfully! Doctor ID: " + doctorID, "Success", JOptionPane.INFORMATION_MESSAGE, checkIcon);
                doctorUpdate.dispose();
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
        new DoctorManagementUpdate();
    }
}
