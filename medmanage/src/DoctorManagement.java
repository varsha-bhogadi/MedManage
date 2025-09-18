//importing
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class DoctorManagement {
    public DoctorManagement(){
        //image sources 
        ImageIcon logo2 = new ImageIcon(DoctorManagement.class.getResource("MedManageLogo.png"));
        ImageIcon logoSmall = new ImageIcon(DoctorManagement.class.getResource("logoSmall.png"));
        ImageIcon doctorAddIcon = new ImageIcon(DoctorManagement.class.getResource("DoctorManagementAdd.png"));
        ImageIcon doctorRemoveIcon = new ImageIcon(DoctorManagement.class.getResource("DoctorManagementRemove.png"));
        ImageIcon doctorSearIcon = new ImageIcon(DoctorManagement.class.getResource("DoctorManagementSearch.png"));
        ImageIcon doctorListIcon = new ImageIcon(DoctorManagement.class.getResource("DoctorManagementList.png"));
        ImageIcon enterIcon = new ImageIcon(DoctorManagement.class.getResource("Enter.png"));
 
        //frame creation
        JFrame doctorManagement = new JFrame();
        doctorManagement.setTitle("Doctor Management");
        doctorManagement.setSize(1440, 900);
        doctorManagement.setLocationRelativeTo(null);
        doctorManagement.setResizable(false);
        doctorManagement.setLayout(null);
        doctorManagement.getContentPane().setBackground(new Color(230, 247, 255));
        doctorManagement.setIconImage(logo2.getImage());

        //Label
        JLabel medLabel = new JLabel();
        medLabel.setText("DOCTOR MANAGEMENT");
        medLabel.setBounds(150, 50, 1440, 150);
        medLabel.setFont(new Font("Times new roman", Font.BOLD, 100)); 
        medLabel.setForeground(Color.BLACK);

        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logoSmall);
        logoLabel.setBounds(50, 50, 200, 150);
        

        //Option buttons
        JButton doctorAdd = new JButton();
        doctorAdd.setBounds(50, 350, 250, 250);
        doctorAdd.setIcon(doctorAddIcon);
        doctorAdd.setBackground(new Color(230, 247, 255));
        doctorAdd.setFocusPainted(false);
        doctorAdd.setBorderPainted(false);

        JButton doctorRemove = new JButton();
        doctorRemove.setBounds(400, 350, 250, 250);
        doctorRemove.setIcon(doctorRemoveIcon);
        doctorRemove.setBackground(new Color(230, 247, 255));
        doctorRemove.setFocusPainted(false);
        doctorRemove.setBorderPainted(false);

        JButton doctorSearch = new JButton();
        doctorSearch.setBounds(770, 350, 250, 250);
        doctorSearch.setIcon(doctorSearIcon);
        doctorSearch.setBackground(new Color(230, 247, 255));
        doctorSearch.setFocusPainted(false);
        doctorSearch.setBorderPainted(false);

        JButton doctorList = new JButton();
        doctorList.setBounds(1140, 350, 250, 250);
        doctorList.setIcon(doctorListIcon);
        doctorList.setBackground(new Color(230, 247, 255));
        doctorList.setFocusPainted(false);
        doctorList.setBorderPainted(false);

        //mouse events
        doctorAdd.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                doctorAdd.setBounds(35, 340, 280, 280);
            }
            public void mouseExited(MouseEvent e){
                doctorAdd.setBounds(50, 350, 250, 250);
            }
            public void mousePressed(MouseEvent e){
                doctorAdd.setBounds(50, 350, 250, 250);
            }
        });

        doctorRemove.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                doctorRemove.setBounds(385, 340, 280, 280);
            }
            public void mouseExited(MouseEvent e){
                doctorRemove.setBounds(400, 350, 250, 250);
            }
            public void mousePressed(MouseEvent e){
                doctorRemove.setBounds(400, 350, 250, 250);
            }
        });

        doctorSearch.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                doctorSearch.setBounds(755, 340, 280, 280);
            }
            public void mouseExited(MouseEvent e){
                doctorSearch.setBounds(770, 350, 250, 250);
            }
            public void mousePressed(MouseEvent e){
                doctorSearch.setBounds(770, 350, 250, 250);
            }
        });

        doctorList.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                doctorList.setBounds(1125, 340, 280, 280);
            }
            public void mouseExited(MouseEvent e){
                doctorList.setBounds(1140, 350, 250, 250);
            }
            public void mousePressed(MouseEvent e){
                doctorList.setBounds(1140, 350, 250, 250);
            }
        });

        //action listener
        // Action Listener
doctorAdd.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String[] options = {"choose one", "Add Doctor", "Update Doctor"};
        JComboBox<String> chooseAddOrUpdate = new JComboBox<>(options);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Select an option:"));
        panel.add(chooseAddOrUpdate);

        // Creating the JOptionPane
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, enterIcon);
        JDialog dialog = optionPane.createDialog("Add or Update Doctor");
        dialog.setIconImage(logo2.getImage());

        // Setting focus on the JComboBox after the dialog is visible
        dialog.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                chooseAddOrUpdate.requestFocusInWindow();
            }
        });

        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                optionPane.setValue(JOptionPane.CANCEL_OPTION);
            }
        });

        dialog.setVisible(true);

        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.CANCEL_OPTION) return;
        if (result == JOptionPane.OK_OPTION) {
            String selectedOption = (String) chooseAddOrUpdate.getSelectedItem();
            if (selectedOption.equals("choose one")) return;
            if (selectedOption.equals("Add Doctor")) new DoctorManagementAdd();
            if (selectedOption.equals("Update Doctor")) new DoctorManagementUpdate();
        }
    }
});

        doctorRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new DoctorManagementRemove();
            }
        });
        doctorSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new DoctorManagementSearch();
            }
        });
        doctorList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new DoctorManagementList();
            }
        });
        //Frame add
        doctorManagement.add(doctorAdd);
        doctorManagement.add(medLabel);
        doctorManagement.add(logoLabel);
        doctorManagement.add(doctorRemove);
        doctorManagement.add(doctorSearch);
        doctorManagement.add(doctorList);

        //visibility and exit
        doctorManagement.setVisible(true);
        doctorManagement.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
}
public static void main(String[] args) {
    new DoctorManagement();
}
}

