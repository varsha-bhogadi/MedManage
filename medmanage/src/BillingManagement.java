import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BillingManagement {

    // Image sources
    ImageIcon enterImage = new ImageIcon(BillingManagement.class.getResource("Enter.png"));;
    ImageIcon logo = new ImageIcon(BillingManagement.class.getResource("MedManageLogo.png"));
    ImageIcon checkImage = new ImageIcon(BillingManagement.class.getResource("Check.png"));
    ImageIcon errorImage = new ImageIcon(BillingManagement.class.getResource("Error.png"));

    public static String patientName, patientDateOfBirth, patientContactNumber, patientEmail, patientId, mode, appointmentId = null;
    public static JTextField amountTextField = new JTextField();
    public static String paymentId;

    public BillingManagement() {
        boolean validAppointment = false;

        while (!validAppointment) {
            // Get Details
            appointmentId = (String) JOptionPane.showInputDialog(
                    null,
                    "Enter AppointmentID",
                    "Enter",
                    JOptionPane.PLAIN_MESSAGE,
                    enterImage,
                    null,
                    ""
            );
            if (appointmentId == null) {
                return;
            }

            // Check appointment status
            try {
                Connection con = DriverManager.getConnection(Global.url, Global.userName, Global.password);
                Statement st = con.createStatement();
                String query1 = "SELECT Status FROM Appointment WHERE AppointmentID=" + appointmentId;
                ResultSet rs = st.executeQuery(query1);
                if (rs.next()) {
                    String status = rs.getString("Status");
                    if (status.equals("Cancelled")) {
                        JOptionPane.showMessageDialog(null, "The appointment status is: " + status, "Status", JOptionPane.INFORMATION_MESSAGE, errorImage);
                    } else if(status.equals("Completed")){
                        JOptionPane.showMessageDialog(null, "The appointment status is: " + status, "Status", JOptionPane.INFORMATION_MESSAGE, checkImage);
                    }
                    else {
                        validAppointment = true;
                    }
                }
                rs.close();
                st.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Frame creation
        JFrame paymentFrame = new JFrame("Payment");
        paymentFrame.setSize(800, 400);
        paymentFrame.setLocationRelativeTo(null);
        paymentFrame.getContentPane().setBackground(new Color(230, 247, 255));
        paymentFrame.setIconImage(logo.getImage());
        paymentFrame.setResizable(false);
        paymentFrame.setLayout(null);

        // Getting details from database
        try {
            Connection con = DriverManager.getConnection(Global.url, Global.userName, Global.password);
            Statement st = con.createStatement();
            String query2 = "SELECT PatientID FROM Appointment WHERE AppointmentID=" + appointmentId;
            ResultSet rs0 = st.executeQuery(query2);
            while (rs0.next()) {
                patientId = rs0.getString("PatientID");
            }
            String query3 = "SELECT FirstName, LastName, DateOfBirth, ContactNumber, Email FROM Patients WHERE PatientId=" + patientId;
            ResultSet rs = st.executeQuery(query3);
            if (rs.next()) {
                patientName = rs.getString("FirstName") + " " + rs.getString("LastName");
                patientDateOfBirth = rs.getString("DateOfBirth");
                patientContactNumber = rs.getString("ContactNumber");
                patientEmail = rs.getString("Email");
            }
            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Font
        Font f1 = new Font("Times New Roman", Font.BOLD, 17);

        // Label
        JLabel patientDetails = new JLabel("Patient Details");
        patientDetails.setBounds(150, 10, 200, 30);
        patientDetails.setFont(new Font("Times New Roman", Font.BOLD, 20));

        JSeparator line1 = new JSeparator();
        line1.setBounds(150, 37, 125, 10);

        JLabel patientNameLabel = new JLabel("Patient Name   : " + patientName);
        patientNameLabel.setBounds(10, 50, 390, 50);
        patientNameLabel.setFont(f1);

        JLabel patientDobLabel = new JLabel("Date Of Birth   : " + patientDateOfBirth);
        patientDobLabel.setBounds(10, 100, 390, 50);
        patientDobLabel.setFont(f1);

        JLabel patientContactLabel = new JLabel("Contact No       : " + patientContactNumber);
        patientContactLabel.setBounds(10, 150, 390, 50);
        patientContactLabel.setFont(f1);

        JSeparator line2 = new JSeparator(SwingConstants.VERTICAL);
        line2.setBounds(410, 37, 10, 180);

        JLabel modeLabel = new JLabel("Mode        : ");
        modeLabel.setBounds(450, 80, 150, 30);
        modeLabel.setFont(f1);

        String[] modeString = {"Choose One", "UPI", "CASH"};
        JComboBox<String> modeBox = new JComboBox<>(modeString);
        modeBox.setBounds(550, 80, 190, 30);
        modeBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mode = (String) modeBox.getSelectedItem();
            }
        });

        JLabel amountLabel = new JLabel("Amount : ");
        amountLabel.setBounds(450, 150, 150, 30);
        amountLabel.setFont(f1);

        amountTextField.setBounds(550, 150, 190, 30);

        // KeyListener for Enter key press on the mode combo box
        modeBox.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    amountTextField.requestFocus();
                }
            }
        });

        JButton payNow = new JButton("Pay Now");
        payNow.setBounds(330, 250, 150, 70);
        payNow.setBackground(Color.BLACK);
        payNow.setForeground(Color.WHITE);
        payNow.setFocusPainted(false);
        payNow.setFont(f1);
        payNow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processPayment(amountTextField,paymentFrame);
            }
        });

        // Frame visibility and frame add
        paymentFrame.add(patientDetails);
        paymentFrame.add(line1);
        paymentFrame.add(patientNameLabel);
        paymentFrame.add(patientDobLabel);
        paymentFrame.add(patientContactLabel);
        paymentFrame.add(line2);
        paymentFrame.add(modeLabel);
        paymentFrame.add(modeBox);
        paymentFrame.add(amountLabel);
        paymentFrame.add(amountTextField);
        paymentFrame.add(payNow);
        paymentFrame.setVisible(true);
        paymentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void processPayment(JTextField amountTextField, JFrame payFrame) {
        String amount = amountTextField.getText();
        if (mode.equals("Choose One") || amount.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a payment mode and enter the amount.");
        } else {
            try {
                Connection con = DriverManager.getConnection(Global.url, Global.userName, Global.password);
                Statement st = con.createStatement();
                st.executeUpdate("UPDATE Appointment SET Status='Completed' WHERE AppointmentID=" + appointmentId);
    
                // Get the current date
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = currentDate.format(formatter);
    
                // Insert payment details into Payment table
                String insertPaymentQuery = "INSERT INTO Payment (AppointmentID, PatientID, Amount, PaymentDate, Mode) " +
                                            "VALUES (" + appointmentId + ", " + patientId + ", " + amount + ", '" + formattedDate + "', '" + mode + "')";
                
                PreparedStatement ps = con.prepareStatement(insertPaymentQuery, Statement.RETURN_GENERATED_KEYS);
                ps.executeUpdate();
    
                // Retrieve the generated PaymentID
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    paymentId = String.valueOf(rs.getInt(1));
                }
                
                rs.close();
                ps.close();
                con.close();
    
                JOptionPane.showMessageDialog(null, "Payment of " + amount + " through " + mode + " is successful.\n\t\tPayment ID: " + paymentId, "Payment", JOptionPane.INFORMATION_MESSAGE, checkImage);
                payFrame.dispose();
                new BillingManagementPdf();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }   

    public static void main(String[] args) {
        new BillingManagement();
    }
}