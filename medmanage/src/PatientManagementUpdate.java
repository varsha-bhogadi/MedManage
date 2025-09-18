import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PatientManagementUpdate {

    // Fields
    private JFrame patientAdd;
    private JTextField firstNameField, lastNameField, dateOfBirthField, bloodPressureField, contactNumberField, emailField, addressField, stateField;
    private JComboBox<String> genderField;
    String patientID, firstName, lastName, dateOfBirth, gender, bloodPressure, contactNumber, email, address, state;

            // Image sources
            ImageIcon logo = new ImageIcon(PatientManagementUpdate.class.getResource("MedManageLogo.png"));
            ImageIcon errorIcon = new ImageIcon(PatientManagementUpdate.class.getResource("Error.png"));
            ImageIcon checkIcon = new ImageIcon(PatientManagementUpdate.class.getResource("Check.png"));
            ImageIcon enterIcon = new ImageIcon(PatientManagementUpdate.class.getResource("Enter.png"));

    // Constructor
    public PatientManagementUpdate() {

        while (true) {
            patientID = (String) JOptionPane.showInputDialog(
                null,
                "Enter PatientID",
                "Enter",
                JOptionPane.PLAIN_MESSAGE,
                enterIcon,
                null,
                ""
            );

            if (patientID == null) {
                return;
            }

            try {
                Connection con = DriverManager.getConnection(Global.url, Global.userName, Global.password);
                PreparedStatement pst = con.prepareStatement("SELECT * FROM PATIENTS WHERE patientID = ?");
                pst.setString(1, patientID);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    firstName = rs.getString("FirstName");
                    lastName = rs.getString("LastName");
                    dateOfBirth = rs.getString("DateOfBirth");
                    gender = rs.getString("Gender");
                    bloodPressure = rs.getString("BloodPressure");
                    contactNumber = rs.getString("ContactNumber");
                    email = rs.getString("Email");
                    address = rs.getString("Address");
                    state = rs.getString("State");
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Patient ID not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Oops unexpected error restart.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Frame creation
        patientAdd = new JFrame();
        patientAdd.setTitle("Patient Form");
        patientAdd.setSize(500, 650);
        patientAdd.setLocationRelativeTo(null);
        patientAdd.setLayout(null);
        patientAdd.setIconImage(logo.getImage());
        patientAdd.setResizable(false);
        patientAdd.getContentPane().setBackground(new Color(230, 247, 255));

        // Labels and text fields
        Font f1 = new Font("Times New Roman", Font.BOLD, 15);

        JLabel firstNameLabel = new JLabel("First Name: ");
        firstNameLabel.setFont(f1);
        firstNameLabel.setBounds(50, 50, 150, 30);
        patientAdd.add(firstNameLabel);
        firstNameField = new JTextField();
        firstNameField.setText(firstName);
        firstNameField.setBounds(250, 50, 200, 30);
        patientAdd.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name: ");
        lastNameLabel.setFont(f1);
        lastNameLabel.setBounds(50, 100, 150, 30);
        patientAdd.add(lastNameLabel);
        lastNameField = new JTextField();
        lastNameField.setText(lastName);
        lastNameField.setBounds(250, 100, 200, 30);
        patientAdd.add(lastNameField);

        JLabel dateOfBirthLabel = new JLabel("Date of Birth (yyyy-mm-dd):");
        dateOfBirthLabel.setFont(f1);
        dateOfBirthLabel.setBounds(50, 150, 200, 30);
        patientAdd.add(dateOfBirthLabel);
        dateOfBirthField = new JTextField();
        dateOfBirthField.setText(dateOfBirth);
        dateOfBirthField.setBounds(250, 150, 200, 30);
        patientAdd.add(dateOfBirthField);

        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setFont(f1);
        genderLabel.setBounds(50, 200, 150, 30);
        patientAdd.add(genderLabel);
        genderField = new JComboBox<>(new String[]{"Select Gender", "Male", "Female", "Other"});
        genderField.setSelectedItem(gender);
        genderField.setBounds(250, 200, 200, 30);
        patientAdd.add(genderField);

        JLabel bloodPressureLabel = new JLabel("Blood Pressure(BP): ");
        bloodPressureLabel.setFont(f1);
        bloodPressureLabel.setBounds(50, 250, 150, 30);
        patientAdd.add(bloodPressureLabel);
        bloodPressureField = new JTextField();
        bloodPressureField.setText(bloodPressure);
        bloodPressureField.setBounds(250, 250, 200, 30);
        patientAdd.add(bloodPressureField);

        JLabel contactNumberLabel = new JLabel("Contact Number: ");
        contactNumberLabel.setFont(f1);
        contactNumberLabel.setBounds(50, 300, 150, 30);
        patientAdd.add(contactNumberLabel);
        contactNumberField = new JTextField();
        contactNumberField.setText(contactNumber);
        contactNumberField.setBounds(250, 300, 200, 30);
        patientAdd.add(contactNumberField);

        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setFont(f1);
        emailLabel.setBounds(50, 350, 150, 30);
        patientAdd.add(emailLabel);
        emailField = new JTextField();
        emailField.setText(email);
        emailField.setBounds(250, 350, 200, 30);
        patientAdd.add(emailField);

        JLabel addressLabel = new JLabel("Address: ");
        addressLabel.setFont(f1);
        addressLabel.setBounds(50, 400, 150, 30);
        patientAdd.add(addressLabel);
        addressField = new JTextField();
        addressField.setText(address);
        addressField.setBounds(250, 400, 200, 30);
        patientAdd.add(addressField);

        JLabel stateLabel = new JLabel("State: ");
        stateLabel.setFont(f1);
        stateLabel.setBounds(50, 450, 150, 30);
        patientAdd.add(stateLabel);
        stateField = new JTextField();
        stateField.setText(state);
        stateField.setBounds(250, 450, 200, 30);
        patientAdd.add(stateField);

        // Button
        JButton updateButton = new JButton("Update");
        updateButton.setFont(f1);
        updateButton.setBackground(Color.BLACK);
        updateButton.setForeground(Color.white);
        updateButton.setBounds(150, 530, 200, 50);
        updateButton.setFocusPainted(false);
        patientAdd.add(updateButton);

        // Action listener for the submit button
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    // Update database
                    updateDatabase(firstNameField.getText(), lastNameField.getText(), genderField.getSelectedItem().toString(),
                            dateOfBirthField.getText(), bloodPressureField.getText(), contactNumberField.getText(),
                            emailField.getText(), addressField.getText(), stateField.getText(), patientID);
                }
            }
        });

        // Add KeyListener to navigate with Enter key
        addEnterKeyListener(firstNameField, lastNameField);
        addEnterKeyListener(lastNameField, dateOfBirthField);
        addEnterKeyListener(dateOfBirthField, genderField);
        addComboBoxKeyListener(genderField, bloodPressureField);
        addEnterKeyListener(bloodPressureField, contactNumberField);
        addEnterKeyListener(contactNumberField, emailField);
        addEnterKeyListener(emailField, addressField);
        addEnterKeyListener(addressField, stateField);
        addEnterKeyListener(stateField, updateButton);

        // Visibility and exiting frame
        patientAdd.setVisible(true);
        patientAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Validate input fields
    private boolean validateInput() {
        if (firstNameField.getText().trim().isEmpty() ||
            lastNameField.getText().trim().isEmpty() ||
            genderField.getSelectedIndex() == 0 ||
            dateOfBirthField.getText().trim().isEmpty() ||
            bloodPressureField.getText().trim().isEmpty() ||
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
    private void updateDatabase(String firstName, String lastName, String gender, String dateOfBirth, String bloodPressure,
                                String contactNumber, String email, String address, String state, String patientID) {
        String updateSQL = "UPDATE Patients SET FirstName = ?, LastName = ?, Gender = ?, DateOfBirth = ?, BloodPressure = ?, ContactNumber = ?, Email = ?, Address = ?, State = ? WHERE PatientID = ?;";

        try (Connection connection = DriverManager.getConnection(Global.url, Global.userName, Global.password);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, dateOfBirth);
            preparedStatement.setString(5, bloodPressure);
            preparedStatement.setString(6, contactNumber);
            preparedStatement.setString(7, email);
            preparedStatement.setString(8, address);
            preparedStatement.setString(9, state);
            preparedStatement.setString(10, patientID);

            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(null, "Patient information updated successfully! Patient ID: " + patientID, "Success", JOptionPane.INFORMATION_MESSAGE, checkIcon);
                patientAdd.dispose();
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
        new PatientManagementUpdate();
    }
}