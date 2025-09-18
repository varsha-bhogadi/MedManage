import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AppointmentBookingStatus {
    int patientId;
    String appointmentStatus, name, appointmentDate;

    // Image sources
    ImageIcon enterImage = new ImageIcon(AppointmentBookingStatus.class.getResource("Enter.png"));
    ImageIcon checkImage = new ImageIcon(AppointmentBookingStatus.class.getResource("Check.png"));
    ImageIcon errorImage = new ImageIcon(AppointmentBookingStatus.class.getResource("Error.png"));
    ImageIcon logo = new ImageIcon(AppointmentBookingStatus.class.getResource("MedManageLogo.png"));

    public AppointmentBookingStatus() {
        // Get details using appointmentId
        String appointmentId = null;
        while (appointmentId == null || appointmentId.isEmpty()) {
            appointmentId = (String) JOptionPane.showInputDialog(
                    null,
                    "Enter AppointmentId",
                    "Enter",
                    JOptionPane.PLAIN_MESSAGE,
                    enterImage,
                    null,
                    ""
            );

            if (appointmentId == null) {
                return;
            }
        }
        int appointmentID = Integer.parseInt(appointmentId);

        // Retrieve data from database
        try (Connection con = DriverManager.getConnection(Global.url, Global.userName, Global.password);
             PreparedStatement ps1 = con.prepareStatement("SELECT PatientID, Status, AppointmentDate FROM Appointment WHERE AppointmentID = ?");
             PreparedStatement ps2 = con.prepareStatement("SELECT FirstName, LastName FROM Patients WHERE PatientID = ?")) {

            ps1.setInt(1, appointmentID);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                patientId = rs1.getInt("PatientID");
                appointmentStatus = rs1.getString("Status");
                appointmentDate = rs1.getString("AppointmentDate");
            } else {
                JOptionPane.showMessageDialog(null, "Appointment details not found", "Error", JOptionPane.ERROR_MESSAGE, errorImage);
                return;
            }

            ps2.setInt(1, patientId);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                name = rs2.getString("FirstName") + " " + rs2.getString("LastName");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error", "Cannot retrieve appointment details", JOptionPane.ERROR_MESSAGE, errorImage);
            e.printStackTrace();
        }

        // Frame creation
        JFrame status = new JFrame("Status");
        status.setIconImage(logo.getImage());
        status.getContentPane().setBackground(new Color(230, 247, 255));
        status.setSize(350, 500);
        status.setLayout(null);
        status.setLocationRelativeTo(null);
        status.setResizable(false);

        Font f1 = new Font("Times New Roman", Font.BOLD, 20);
        Font f2 = new Font("Times New Roman", Font.BOLD, 13);


        JLabel patientDetails = new JLabel("PATIENT DETAILS");
        patientDetails.setForeground(Color.black);
        patientDetails.setBounds(75, 0, 300, 100);
        patientDetails.setFont(f1);

        JLabel StatusLabel = new JLabel("STATUS");
        StatusLabel.setForeground(Color.black);
        StatusLabel.setBounds(130, 200, 300, 40);
        StatusLabel.setFont(f1);

        JSeparator line1 = new JSeparator();
        line1.setBounds(130, 235, 80, 10);

        JSeparator line = new JSeparator();
        line.setBounds(90, 75, 150, 10);

        JLabel id = new JLabel("Patient Id              : " + patientId);
        JLabel nameLabel = new JLabel("Name                     : " + name);
        JLabel dob = new JLabel("Appointment Date : " + appointmentDate);
        id.setBounds(10, 80, 340, 50);
        id.setFont(f2);
        nameLabel.setBounds(10, 120, 340, 50);
        nameLabel.setFont(f2);
        dob.setBounds(10, 160, 340, 50);
        dob.setFont(f2);

        JButton statusButton = new JButton(appointmentStatus);
        statusButton.setFont(new Font("Times New Roman", Font.BOLD, 50));
        statusButton.setBounds(0, 250, 350, 100);
        statusButton.setFocusPainted(false);
        statusButton.setBorderPainted(false);
        statusButton.setBackground(new Color(102, 205, 170));
        if (appointmentStatus.equals("Scheduled")) statusButton.setForeground(Color.YELLOW);
        if (appointmentStatus.equals("Completed")) statusButton.setForeground(Color.GREEN);
        if (appointmentStatus.equals("Cancelled")) statusButton.setForeground(Color.RED);

        JButton cancelButton = new JButton("Cancel Appointment");
        cancelButton.setBounds(70, 390, 200, 50);
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            if(appointmentStatus.equals("Scheduled")){
                try (Connection con = DriverManager.getConnection(Global.url, Global.userName, Global.password);
                     PreparedStatement ps = con.prepareStatement("UPDATE Appointment SET Status = ? WHERE AppointmentID = ?")) {

                    ps.setString(1, "Cancelled");
                    ps.setInt(2, appointmentID);
                    int rowsUpdated = ps.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(null, "Appointment cancelled successfully", "Appointment cancelled successfully", JOptionPane.INFORMATION_MESSAGE,checkImage);
                        statusButton.setForeground(Color.RED);
                        statusButton.setText("Cancelled");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error", "No appointment found with the given ID", JOptionPane.ERROR_MESSAGE, errorImage);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Cannot cancel appointment at this moment", "Error", JOptionPane.ERROR_MESSAGE, errorImage);
                    ex.printStackTrace();
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Already cancelled or completed ", "Error", JOptionPane.ERROR_MESSAGE, errorImage);
            }
        }
        });


        // Frame add
        status.add(patientDetails);
        status.add(line);
        status.add(id);
        status.add(nameLabel);
        status.add(dob);
        status.add(StatusLabel);
        status.add(statusButton);
        status.add(cancelButton);
        status.add(line1);

        // Visibility
        status.setVisible(true);
        status.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new AppointmentBookingStatus();
    }
}
