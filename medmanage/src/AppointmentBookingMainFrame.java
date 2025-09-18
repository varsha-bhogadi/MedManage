import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AppointmentBookingMainFrame {
    public static int patientid;
    public static boolean proceed = false;

    //image sources
    ImageIcon logo2 = new ImageIcon(AppointmentBookingMainFrame.class.getResource("MedManageLogo.png"));
    ImageIcon logoSmall = new ImageIcon(AppointmentBookingMainFrame.class.getResource("logoSmall.png"));
    ImageIcon doctorAddIcon = new ImageIcon(AppointmentBookingMainFrame.class.getResource("BookingAppointmentHavindId.png"));
    ImageIcon doctorRemoveIcon = new ImageIcon(AppointmentBookingMainFrame.class.getResource("BookingAppointmentNewPatient.png"));
    ImageIcon doctorSearIcon = new ImageIcon(AppointmentBookingMainFrame.class.getResource("BookingAppointmentStatus.png"));
    ImageIcon doctorListIcon = new ImageIcon(AppointmentBookingMainFrame.class.getResource("BookingAppointmentList.png"));
    ImageIcon enterImage =new ImageIcon(AppointmentBookingMainFrame.class.getResource("Enter.png"));
    ImageIcon errorImage = new ImageIcon(AppointmentBookingMainFrame.class.getResource("Error.png"));


    
    public AppointmentBookingMainFrame(){

        //frame creation
        JFrame appointmentBooking = new JFrame();
        appointmentBooking.setTitle("Appointment Booking");
        appointmentBooking.setSize(1440, 900);
        appointmentBooking.setLocationRelativeTo(null);
        appointmentBooking.setResizable(false);
        appointmentBooking.setLayout(null);
        appointmentBooking.getContentPane().setBackground(new Color(230, 247, 255));
        appointmentBooking.setIconImage(logo2.getImage());

        //Label
        JLabel medLabel = new JLabel();
        medLabel.setText("APPOINTMENT BOOKING");
        medLabel.setBounds(130, 50, 1440, 150);
        medLabel.setFont(new Font("Times new roman", Font.BOLD, 100)); 
        medLabel.setForeground(Color.BLACK);

        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logoSmall);
        logoLabel.setBounds(30, 50, 200, 150);
        

        //Option buttons
        JButton havingPatientId = new JButton();
        havingPatientId.setBounds(50, 350, 250, 250);
        havingPatientId.setIcon(doctorAddIcon);
        havingPatientId.setBackground(new Color(230, 247, 255));
        havingPatientId.setFocusPainted(false);
        havingPatientId.setBorderPainted(false);

        JButton newPatient = new JButton();
        newPatient.setBounds(400, 350, 250, 250);
        newPatient.setIcon(doctorRemoveIcon);
        newPatient.setBackground(new Color(230, 247, 255));
        newPatient.setFocusPainted(false);
        newPatient.setBorderPainted(false);

        JButton appointmentStatusOrUpdate = new JButton();
        appointmentStatusOrUpdate.setBounds(770, 350, 250, 250);
        appointmentStatusOrUpdate.setIcon(doctorSearIcon);
        appointmentStatusOrUpdate.setBackground(new Color(230, 247, 255));
        appointmentStatusOrUpdate.setFocusPainted(false);
        appointmentStatusOrUpdate.setBorderPainted(false);

        JButton appointmentsList = new JButton();
        appointmentsList.setBounds(1140, 350, 250, 250);
        appointmentsList.setIcon(doctorListIcon);
        appointmentsList.setBackground(new Color(230, 247, 255));
        appointmentsList.setFocusPainted(false);
        appointmentsList.setBorderPainted(false);

        //mouse events
        havingPatientId.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                havingPatientId.setBounds(35, 340, 280, 280);
            }
            public void mouseExited(MouseEvent e){
                havingPatientId.setBounds(50, 350, 250, 250);
            }
            public void mousePressed(MouseEvent e){
                havingPatientId.setBounds(50, 350, 250, 250);
            }
        });

        newPatient.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                newPatient.setBounds(385, 340, 280, 280);
            }
            public void mouseExited(MouseEvent e){
                newPatient.setBounds(400, 350, 250, 250);
            }
            public void mousePressed(MouseEvent e){
                newPatient.setBounds(400, 350, 250, 250);
            }
        });

        appointmentStatusOrUpdate.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                appointmentStatusOrUpdate.setBounds(755, 340, 280, 280);
            }
            public void mouseExited(MouseEvent e){
                appointmentStatusOrUpdate.setBounds(770, 350, 250, 250);
            }
            public void mousePressed(MouseEvent e){
                appointmentStatusOrUpdate.setBounds(770, 350, 250, 250);
            }
        });

        appointmentsList.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                appointmentsList.setBounds(1125, 340, 280, 280);
            }
            public void mouseExited(MouseEvent e){
                appointmentsList.setBounds(1140, 350, 250, 250);
            }
            public void mousePressed(MouseEvent e){
                appointmentsList.setBounds(1140, 350, 250, 250);
            }
        });

        //action listener
        // Action Listener
        havingPatientId.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                havePatientIDAction();
            }
        });

        newPatient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new PatientManagementAdd();
                proceed = true;
            }
        });

        appointmentStatusOrUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new AppointmentBookingStatus();
            }
        });

        appointmentsList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new AppointmentBookingList();
            }
        });
        //Frame add
        appointmentBooking.add(havingPatientId);
        appointmentBooking.add(medLabel);
        appointmentBooking.add(logoLabel);
        appointmentBooking.add(newPatient);
        appointmentBooking.add(appointmentStatusOrUpdate);
        appointmentBooking.add(appointmentsList);

        //visibility and exit
        appointmentBooking.setVisible(true);
        appointmentBooking.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    // Action for "Having PatientID" button
    private void havePatientIDAction() {
        String id = null;
        while (true) {
            id = (String) JOptionPane.showInputDialog(
                null,
                "Enter Patient ID",
                "Enter",
                JOptionPane.PLAIN_MESSAGE,
                enterImage,
                null,
                ""
            );

            if (id == null) {
                return; 
            }

            try {
                patientid = Integer.parseInt(id);
                if (isValidPatientID(patientid)) {
                    new AppointmentBooking();
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Patient not found or invalid input.", 
                                                "Invalid Input", JOptionPane.ERROR_MESSAGE, errorImage);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid Patient ID.", 
                                                "Invalid Input", JOptionPane.ERROR_MESSAGE, errorImage);
            }
        }    
    }

    // Check if the Patient ID exists in the database
    private boolean isValidPatientID(int id) {
        boolean exists = false;
        try (Connection con = DriverManager.getConnection(Global.url, Global.userName, Global.password);
             PreparedStatement ps = con.prepareStatement("SELECT 1 FROM Patients WHERE patientID = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                exists = rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
    public static void main(String[] args) {
        new AppointmentBookingMainFrame();
    }
}
