import java.io.*;
import javax.swing.*;

public class AppointmentBookingConfirmation {
    ImageIcon check = new ImageIcon(AppointmentBookingConfirmation.class.getResource("Check.png"));
    private JDialog loadingDialog;

    public AppointmentBookingConfirmation() {
        initialize();
    }

    private void initialize() {
        String doctorappointmentID = Integer.toString(AppointmentBooking.appointmentID);
        String doctorName = (String) AppointmentBooking.doctorBox.getSelectedItem();
        String email = AppointmentBooking.patientEmail;
        String appointmentDate = AppointmentBooking.dateField.getText();
        String patientName = AppointmentBooking.patientName;
        String emailScriptPath = new File("EmailFiles//appointment_booking_email.py").getAbsolutePath();

        // Show loading dialog
        loadingDialog = new JDialog();
        loadingDialog.setTitle("Loading");
        loadingDialog.setModal(true);
        loadingDialog.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel loadingLabel = new JLabel("Please wait, Processing...");
        loadingLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(loadingLabel);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        panel.add(progressBar);

        loadingDialog.add(panel);
        loadingDialog.pack();
        loadingDialog.setLocationRelativeTo(null);

        SwingUtilities.invokeLater(() -> loadingDialog.setVisible(true));

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                ProcessBuilder pb = new ProcessBuilder("python", emailScriptPath,doctorName, email, appointmentDate, doctorName, patientName);
                pb.redirectErrorStream(true);

                try {
                    Process p = pb.start();

                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }
                    }

                    int exitCode = p.waitFor();
                    if (exitCode != 0) {
                        System.out.println("Python script exited with error code: " + exitCode);
                    } else {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(null, "Appointment Booked successfully! your AppointmentID :"+doctorappointmentID, "Success", JOptionPane.INFORMATION_MESSAGE, check);
                        });
                        System.out.println("Email sent successfully!");
                    }
                } catch (IOException e) {
                    System.err.println("IO Exception occurred: " + e.getMessage());
                } catch (InterruptedException e) {
                    System.err.println("Process was interrupted: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                SwingUtilities.invokeLater(() -> loadingDialog.dispose());
            }
        };
        worker.execute();
    }

    public static void main(String[] args) {
        // For testing the functionality
        SwingUtilities.invokeLater(AppointmentBookingConfirmation::new);
    }
}
