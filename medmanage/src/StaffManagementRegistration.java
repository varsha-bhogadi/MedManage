import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class StaffManagementRegistration {

    ImageIcon check = new ImageIcon(StaffManagementRegistration.class.getResource("Check.png"));

    JDialog loadingDialog;

    public StaffManagementRegistration() {
        initialize();
    }

    private void initialize() {
        String staffID = Integer.toString(StaffManagementAdd.staffID); 
        String staffName = StaffManagementAdd.firstNameField.getText() + " " + StaffManagementAdd.lastNameField.getText(); 
        String contactNumber = StaffManagementAdd.contactNumberField.getText(); 
        String email = StaffManagementAdd.emailField.getText(); 
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm");
        String registrationDate = currentDateTime.format(formatter);

        String emailScriptPath = new File("EmailFiles//staff_registration_email.py").getAbsolutePath();

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

        // Add a loading animation (progress bar)
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        panel.add(progressBar);

        loadingDialog.add(panel);
        loadingDialog.pack();
        loadingDialog.setLocationRelativeTo(null);

        // Use invokeLater to ensure dialog creation on the EDT
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                loadingDialog.setVisible(true);
            }
        });

        // SwingWorker to run the Python script in the background
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                // Run your Python script here
                ProcessBuilder pb = new ProcessBuilder("python", emailScriptPath, staffName, contactNumber, email, registrationDate, staffID);
                pb.redirectErrorStream(true);

                try {
                    Process p = pb.start();

                    // Capture and print the output from the process
                    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }

                    int exitCode = p.waitFor();
                    if (exitCode != 0) {
                        System.out.println("Python script exited with error code: " + exitCode);
                    } else {
                        JOptionPane.showMessageDialog(null, "Staff information submitted successfully! Staff ID: " + staffID, "Success", JOptionPane.INFORMATION_MESSAGE, check);
                        System.out.println("Email sent successfully!");
                    }
                } catch (IOException e) {
                    System.err.println("IO Exception occurred: " + e.getMessage());
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    System.err.println("Process was interrupted: " + e.getMessage());
                    e.printStackTrace();
                } 
                return null;
            }

            @Override
            protected void done() {
                // Use invokeLater to ensure the dialog is disposed of on the EDT
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        loadingDialog.dispose();
                    }
                });
            }
        };
        worker.execute();
    }

    public static void main(String[] args) {
        // Initialization will happen in another class
    }
}
