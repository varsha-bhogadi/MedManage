import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DoctorManagementAdd {

    // Fields
    public JFrame doctorAdd;
    public static int doctorID;
    public static JTextField firstNameField, lastNameField, specializationField, contactNumberField, emailField, addressField, stateField;
    public JComboBox<String> genderField;

    // Image sources
    ImageIcon logo = new ImageIcon(DoctorManagementAdd.class.getResource("MedManageLogo.png"));
    ImageIcon errorIcon = new ImageIcon(DoctorManagementAdd.class.getResource("Error.png"));
    ImageIcon checkIcon = new ImageIcon(DoctorManagementAdd.class.getResource("Check.png"));

    // Constructor
    public DoctorManagementAdd() {

        // Frame creation
        doctorAdd = new JFrame();
        doctorAdd.setTitle("Doctor Form");
        doctorAdd.setSize(500, 550);
        doctorAdd.setLocationRelativeTo(null);
        doctorAdd.setLayout(null);
        doctorAdd.setIconImage(logo.getImage());
        doctorAdd.setResizable(false);
        doctorAdd.getContentPane().setBackground(new Color(230, 247, 255));

        // Labels and text fields
        Font f1 = new Font("Times New Roman", Font.BOLD, 15);

        JLabel firstNameLabel = new JLabel("First Name: ");
        firstNameLabel.setFont(f1);
        firstNameLabel.setBounds(50, 50, 150, 30);
        doctorAdd.add(firstNameLabel);
        firstNameField = new JTextField();
        firstNameField.setBounds(250, 50, 200, 30);
        doctorAdd.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name: ");
        lastNameLabel.setFont(f1);
        lastNameLabel.setBounds(50, 100, 150, 30);
        doctorAdd.add(lastNameLabel);
        lastNameField = new JTextField();
        lastNameField.setBounds(250, 100, 200, 30);
        doctorAdd.add(lastNameField);

        JLabel specializationLabel = new JLabel("Specialization: ");
        specializationLabel.setFont(f1);
        specializationLabel.setBounds(50, 150, 150, 30);
        doctorAdd.add(specializationLabel);
        specializationField = new JTextField();
        specializationField.setBounds(250, 150, 200, 30);
        doctorAdd.add(specializationField);

        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setFont(f1);
        genderLabel.setBounds(50, 200, 150, 30);
        doctorAdd.add(genderLabel);
        genderField = new JComboBox<>(new String[]{"Select Gender", "Male", "Female", "Other"});
        genderField.setBounds(250, 200, 200, 30);
        doctorAdd.add(genderField);
        genderField.setSelectedIndex(0);

        JLabel contactNumberLabel = new JLabel("Contact Number: ");
        contactNumberLabel.setFont(f1);
        contactNumberLabel.setBounds(50, 250, 150, 30);
        doctorAdd.add(contactNumberLabel);
        contactNumberField = new JTextField();
        contactNumberField.setBounds(250, 250, 200, 30);
        doctorAdd.add(contactNumberField);

        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setFont(f1);
        emailLabel.setBounds(50, 300, 150, 30);
        doctorAdd.add(emailLabel);
        emailField = new JTextField();
        emailField.setBounds(250, 300, 200, 30);
        doctorAdd.add(emailField);

        JLabel addressLabel = new JLabel("Address: ");
        addressLabel.setFont(f1);
        addressLabel.setBounds(50, 350, 150, 30);
        doctorAdd.add(addressLabel);
        addressField = new JTextField();
        addressField.setBounds(250, 350, 200, 30);
        doctorAdd.add(addressField);

        JLabel stateLabel = new JLabel("State: ");
        stateLabel.setFont(f1);
        stateLabel.setBounds(50, 400, 150, 30);
        doctorAdd.add(stateLabel);
        stateField = new JTextField();
        stateField.setBounds(250, 400, 200, 30);
        doctorAdd.add(stateField);

        // Button
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.setBounds(150, 450, 200, 50);
        submitButton.setFocusPainted(false);
        doctorAdd.add(submitButton);

        // Action listener for the submit button
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    insertIntoDatabase(firstNameField.getText(), lastNameField.getText(), specializationField.getText(),
                            (String)genderField.getSelectedItem(), contactNumberField.getText(), emailField.getText(),
                            addressField.getText(), stateField.getText());
                }
            }
        });

        // Adding key listeners for Enter key navigation
        addEnterKeyListener(firstNameField, lastNameField);
        addEnterKeyListener(lastNameField, specializationField);
        addEnterKeyListener(specializationField, genderField);
        addComboBoxKeyListener(genderField, contactNumberField);
        addEnterKeyListener(contactNumberField, emailField);
        addEnterKeyListener(emailField, addressField);
        addEnterKeyListener(addressField, stateField);
        addEnterKeyListener(stateField, submitButton);

        // Focus on the first text field
        firstNameField.requestFocusInWindow();

        // Visibility and exiting frame
        doctorAdd.setVisible(true);
        doctorAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Validate input fields
    private boolean validateInput() {
        if (firstNameField.getText().trim().isEmpty() ||
                lastNameField.getText().trim().isEmpty() ||
                specializationField.getText().trim().isEmpty() ||
                contactNumberField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() ||
                addressField.getText().trim().isEmpty() ||
                stateField.getText().trim().isEmpty() ||
                genderField.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please fill all required fields.", "Input Error", JOptionPane.PLAIN_MESSAGE, errorIcon);
            return false;
        }
        return true;
    }

    private void insertIntoDatabase(String firstName, String lastName, String specialization, String gender, String contactNumber,
                                    String email, String address, String state) {
        String insertSQL = "INSERT INTO Doctors (FirstName, LastName, Specialization, Gender, ContactNumber, Email, Address, State) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(Global.url, Global.userName, Global.password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, specialization);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, contactNumber);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, address);
            preparedStatement.setString(8, state);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        doctorID = generatedKeys.getInt(1);
                        new DoctorManagementRegistration();
                        doctorAdd.dispose();
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
        new DoctorManagementAdd();
    }
}
