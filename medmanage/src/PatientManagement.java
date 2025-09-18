//importing
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class PatientManagement {
    public PatientManagement(){
        //image sources
        ImageIcon logo2 = new ImageIcon(PatientManagement.class.getResource("MedManageLogo.png"));
        ImageIcon logoSmall = new ImageIcon(PatientManagement.class.getResource("logoSmall.png"));
        ImageIcon patientAddIcon = new ImageIcon(PatientManagement.class.getResource("PatientmanagementAdd.png"));
        ImageIcon patientRemoveIcon = new ImageIcon(PatientManagement.class.getResource("PatientManagementRemove.png"));
        ImageIcon patientSearIcon = new ImageIcon(PatientManagement.class.getResource("PatientManagementSearch.png"));
        ImageIcon patientListIcon =new ImageIcon(PatientManagement.class.getResource("PatientManagementList.png"));
        ImageIcon EnterIcon = new ImageIcon(PatientManagement.class.getResource("Enter.png"));

        //frame creation
        JFrame patientManagement = new JFrame();
        patientManagement.setSize(1440, 900);
        patientManagement.setLocationRelativeTo(null);
        patientManagement.setTitle("Patient Management");
        patientManagement.setResizable(false);
        patientManagement.setLayout(null);
        patientManagement.getContentPane().setBackground(new Color(230, 247, 255));
        patientManagement.setIconImage(logo2.getImage());

        //Label
        JLabel medLabel = new JLabel();
        medLabel.setText("PATIENT MANAGEMENT");
        medLabel.setBounds(150, 50, 1440, 150);
        medLabel.setFont(new Font("Times new roman", Font.BOLD, 100)); 
        medLabel.setForeground(Color.BLACK);

        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logoSmall);
        logoLabel.setBounds(50, 50, 200, 150);
        

        //Option buttons
        JButton patientAdd = new JButton();
        patientAdd.setBounds(50, 350, 250, 250);
        patientAdd.setIcon(patientAddIcon);
        patientAdd.setBackground(new Color(230, 247, 255));
        patientAdd.setFocusPainted(false);
        patientAdd.setBorderPainted(false);

        JButton patientRemove = new JButton();
        patientRemove.setBounds(400, 350, 250, 250);
        patientRemove.setIcon(patientRemoveIcon);
        patientRemove.setBackground(new Color(230, 247, 255));
        patientRemove.setFocusPainted(false);
        patientRemove.setBorderPainted(false);

        JButton patientSearch = new JButton();
        patientSearch.setBounds(770, 350, 250, 250);
        patientSearch.setIcon(patientSearIcon);
        patientSearch.setBackground(new Color(230, 247, 255));
        patientSearch.setFocusPainted(false);
        patientSearch.setBorderPainted(false);

        JButton patientList = new JButton();
        patientList.setBounds(1140, 350, 250, 250);
        patientList.setIcon(patientListIcon);
        patientList.setBackground(new Color(230, 247, 255));
        patientList.setFocusPainted(false);
        patientList.setBorderPainted(false);

        //mouse events
        patientAdd.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                patientAdd.setBounds(35, 340, 280, 280);
            }
            public void mouseExited(MouseEvent e){
                patientAdd.setBounds(50, 350, 250, 250);
            }
            public void mousePressed(MouseEvent e){
                patientAdd.setBounds(50, 350, 250, 250);
            }
        });

        patientRemove.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                patientRemove.setBounds(385, 340, 280, 280);
            }
            public void mouseExited(MouseEvent e){
                patientRemove.setBounds(400, 350, 250, 250);
            }
            public void mousePressed(MouseEvent e){
                patientRemove.setBounds(400, 350, 250, 250);
            }
        });

        patientSearch.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                patientSearch.setBounds(755, 340, 280, 280);
            }
            public void mouseExited(MouseEvent e){
                patientSearch.setBounds(770, 350, 250, 250);
            }
            public void mousePressed(MouseEvent e){
                patientSearch.setBounds(770, 350, 250, 250);
            }
        });

        patientList.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                patientList.setBounds(1125, 340, 280, 280);
            }
            public void mouseExited(MouseEvent e){
                patientList.setBounds(1140, 350, 250, 250);
            }
            public void mousePressed(MouseEvent e){
                patientList.setBounds(1140, 350, 250, 250);
            }
        });

        //Action Listner
        patientAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String[] options = {"choose one", "Add Patient", "Update Patient"};
                JComboBox<String> chooseAddOrUpdate = new JComboBox<>(options);
                
                JPanel panel = new JPanel();
                panel.add(new JLabel("Select an option:"));
                panel.add(chooseAddOrUpdate);
            
                // Creating the JOptionPane
                JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, EnterIcon);
                JDialog dialog = optionPane.createDialog("Add or Update Patient");
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
                    if (selectedOption.equals("Add Patient")) new PatientManagementAdd();
                    if (selectedOption.equals("Update Patient")) new PatientManagementUpdate();
                }
            }
        });
        

        patientRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
              new PatientManagementRemove();
            }
        });
        patientSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new PatientManagementSearch();
            }
        });
        patientList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new PatientManagementList();
            }
        });
        //Frame add
        patientManagement.add(patientAdd);
        patientManagement.add(medLabel);
        patientManagement.add(logoLabel);
        patientManagement.add(patientRemove);
        patientManagement.add(patientList);
        patientManagement.add(patientSearch);

        //visability
        patientManagement.setVisible(true);
        patientManagement.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
}
public static void main(String[] args) {
    new PatientManagement();    
    }
}