import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*; 
import java.util.ArrayList;

public class AppointmentBooking {
    public static String patientName, patientDateOfBirth, patientContactNumber;
    public static int patientID, appointmentID;
    public static String patientEmail,DoctorID;
    public static JTextField dateField = new JTextField();
    ArrayList<String> specializations = new ArrayList<>();
    public static JComboBox<String> doctorBox = new JComboBox<>();

    //image sources
    ImageIcon logo = new ImageIcon(AppointmentBooking.class.getResource("MedManageLogo.png"));
    ImageIcon checkIcon = new ImageIcon(AppointmentBooking.class.getResource("check.png"));
    ImageIcon errorIcon = new ImageIcon(AppointmentBooking.class.getResource("Error.png"));
    ImageIcon enterIcon = new ImageIcon(AppointmentBooking.class.getResource("Enter.png"));


    public static String date;

    public AppointmentBooking() {
        // Frame creation
        JFrame booking = new JFrame("Booking Appointment");
        booking.setResizable(false);
        booking.setSize(800, 400);
        booking.getContentPane().setBackground(new Color(230, 247, 255));
        booking.setLocationRelativeTo(null);
        booking.setLayout(null);
        booking.setIconImage(logo.getImage());

        // Getting details from database
        try { 
            Connection con = DriverManager.getConnection(Global.url, Global.userName, Global.password);
            Statement st = con.createStatement();
            String query = "SELECT FirstName, LastName, DateOfBirth, ContactNumber, Email FROM Patients WHERE PatientId=" + AppointmentBookingMainFrame.patientid;
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                patientName = rs.getString("FirstName") + " " + rs.getString("LastName");
                patientDateOfBirth = rs.getString("DateOfBirth");
                patientContactNumber = rs.getString("ContactNumber");
                patientEmail = rs.getString("Email");
                patientID = AppointmentBookingMainFrame.patientid;
            }
            rs.close();

            String query2 = "SELECT DISTINCT Specialization FROM Doctors";
            ResultSet rs2 = st.executeQuery(query2);
            while (rs2.next()) {
                specializations.add(rs2.getString("Specialization"));
            }
            specializations.add(0, "Choose One");
            rs2.close();

            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Font
        Font f1 = new Font("Times New Roman", Font.BOLD, 17);

        //label
        JLabel patientDetails = new JLabel("Patient Details");
        patientDetails.setBounds(150, 5, 200, 30);
        patientDetails.setFont(new Font("Times New Roman", Font.BOLD, 20));

        JLabel doctorSelection = new JLabel("Select Doctor");
        doctorSelection.setBounds(550, 5, 200, 30);
        doctorSelection.setFont(new Font("Times New Roman", Font.BOLD, 20));

        JSeparator line0 = new JSeparator(SwingConstants.HORIZONTAL);
        line0.setBounds(150, 30, 130, 20);

        JSeparator line1 = new JSeparator(SwingConstants.HORIZONTAL);
        line1.setBounds(550, 30, 110, 20);

        // Labels
        JLabel name = new JLabel("Patient Name : " + patientName);
        JLabel dateOfBirth = new JLabel("Date Of Birth : " + patientDateOfBirth);
        JLabel contact = new JLabel("Contact           : " + patientContactNumber);
        name.setBounds(10, 40, 400, 25);
        dateOfBirth.setBounds(10, 95, 400, 25);
        contact.setBounds(10, 150, 400, 25);
        name.setFont(f1);
        dateOfBirth.setFont(f1);
        contact.setFont(f1);

        // Vertical line (separator)
        JSeparator line = new JSeparator(SwingConstants.VERTICAL);
        line.setBounds(402, 30, 4, 175);

        // Selecting specialization
        JLabel select = new JLabel("Select Specialization :");
        select.setBounds(410, 40, 170, 25);
        select.setFont(f1);

        String[] temp = specializations.toArray(new String[0]);
        JComboBox<String> selectBox = new JComboBox<>(temp);
        selectBox.setBounds(570, 40, 210, 30);
        selectBox.setSelectedIndex(0);
        selectBox.setFocusable(true);

        //button
        JButton checkDoctors = new JButton("Check Doctors");
        checkDoctors.setFont(f1);
        checkDoctors.setBounds(570, 85, 210, 50);
        checkDoctors.setBackground(Color.BLACK);
        checkDoctors.setForeground(Color.WHITE);
        checkDoctors.setFocusPainted(false);

        // Label for selecting doctor
        JLabel selectDoctor = new JLabel("Select Doctor            :");
        selectDoctor.setBounds(410, 150, 170, 25);
        selectDoctor.setFont(f1);

        // Combobox for displaying doctors
        doctorBox.setBounds(570, 150, 210, 30);

        //action listener for check doctor 
        checkDoctors.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedSpecialization = (String) selectBox.getSelectedItem();
                if (selectedSpecialization.equals("Choose One")) {
                    JOptionPane.showMessageDialog(booking, "Please select a specialization.", "Input", JOptionPane.INFORMATION_MESSAGE, enterIcon);
                    selectBox.requestFocusInWindow();
                    return;
                }

                try {
                    doctorBox.requestFocusInWindow();
                    Connection con = DriverManager.getConnection(Global.url, Global.userName, Global.password);
                    Statement st = con.createStatement();
                    String query = "SELECT DoctorID, FirstName, LastName FROM Doctors WHERE Specialization = '" + selectedSpecialization + "'";
                    ResultSet rs = st.executeQuery(query);
                    doctorBox.removeAllItems();
                    while (rs.next()) {
                        DoctorID = rs.getString(1);
                        doctorBox.addItem(rs.getString("FirstName") + " " + rs.getString("LastName"));
                    }
                    rs.close();
                    st.close();
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //label
        JLabel enterDate = new JLabel("Enter Date(Y-M-D) : ");
        enterDate.setFont(f1);
        enterDate.setBounds(240, 255, 200, 50);

        //text input
        dateField.setBounds(400, 260, 150, 40);

        JLabel bookAppointment = new JLabel("Book Appointment");
        bookAppointment.setBounds(300, 210, 250, 30);
        bookAppointment.setFont(new Font("Times New Roman", Font.BOLD, 25));

        JSeparator line3 = new JSeparator(SwingConstants.HORIZONTAL);
        line3.setBounds(300, 240, 200, 20);

        //button
        JButton bookButton = new JButton("Book Appointment");
        bookButton.setBounds(240, 300, 310, 50);
        bookButton.setFont(f1);
        bookButton.setBackground(Color.BLACK);
        bookButton.setForeground(Color.WHITE);
        bookButton.setFocusPainted(false);

        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedDoctor = (String) doctorBox.getSelectedItem();
                String appointmentDate = dateField.getText();

                if (selectedDoctor == null || selectedDoctor.isEmpty()) {
                    JOptionPane.showMessageDialog(booking, "Please select a doctor.", "Select doctor", JOptionPane.INFORMATION_MESSAGE, enterIcon);
                    doctorBox.requestFocusInWindow();
                    return;
                }
                if (appointmentDate == null || appointmentDate.isEmpty()) {
                    JOptionPane.showMessageDialog(booking, "Please enter a date.", "Enter Date", JOptionPane.INFORMATION_MESSAGE, enterIcon);
                    dateField.requestFocusInWindow();
                    return;
                }
                
                try {
                    Connection con = DriverManager.getConnection(Global.url, Global.userName, Global.password);
                    String query = "INSERT INTO Appointment (PatientID, DoctorID, AppointmentDate, Status) VALUES (?, ?, ?, 'Scheduled')";
                    PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    pst.setInt(1, patientID);
                    pst.setString(2, DoctorID);
                    pst.setString(3, appointmentDate);
                    int rowsInserted = pst.executeUpdate();
                    if (rowsInserted > 0) {
                        try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                appointmentID = generatedKeys.getInt(1);    
                                new AppointmentBookingConfirmation();
                                booking.dispose();
                            }
                        }
                    }
                    pst.close();
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        doctorBox.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    dateField.requestFocusInWindow();
                }
            }
        });

        // Frame add and visibility
        booking.add(patientDetails);
        booking.add(line0);
        booking.add(doctorSelection);
        booking.add(line1);
        booking.add(bookAppointment);
        booking.add(line3);
        booking.add(name);
        booking.add(dateOfBirth);
        booking.add(contact);
        booking.add(select);
        booking.add(line);
        booking.add(selectBox);
        booking.add(checkDoctors);
        booking.add(selectDoctor);
        booking.add(doctorBox);
        booking.add(enterDate);
        booking.add(dateField);
        booking.add(bookButton);
        booking.setVisible(true);
        booking.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new AppointmentBooking();
    }
}
