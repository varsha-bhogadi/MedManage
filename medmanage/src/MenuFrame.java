//importing packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuFrame{
    public MenuFrame(){

        //image sources
        ImageIcon logo2 = new ImageIcon(MenuFrame.class.getResource("MedManageLogo.png"));
        ImageIcon patientIcon = new ImageIcon(MenuFrame.class.getResource("MenuPatientManagementIcon.png"));
        ImageIcon doctorIcon = new ImageIcon(MenuFrame.class.getResource("MenuDoctorManagementIcon.png"));
        ImageIcon staffIcon = new ImageIcon(MenuFrame.class.getResource("MenuStaffManagementIcon.png"));
        ImageIcon inventoryIcon = new ImageIcon(MenuFrame.class.getResource("MenuInventorymanagementicon.png"));
        ImageIcon billingIcon = new ImageIcon(MenuFrame.class.getResource("MenuBillingManagementIcon.png"));
        ImageIcon appointIcon = new ImageIcon(MenuFrame.class.getResource("MenuBookingAppointmentIcon.png"));



        //frame creation
        JFrame menuFrame = new JFrame();
        menuFrame.setSize(1440, 900);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setTitle("Menu");
        menuFrame.setResizable(false);
        menuFrame.setLayout(null);
        menuFrame.getContentPane().setBackground(new Color(230, 247, 255));
        menuFrame.setIconImage(logo2.getImage());

        //labels
        JButton patientInfo = new JButton();
        patientInfo.setIcon(patientIcon);
        patientInfo.setBounds(30, 0, 400, 400);
        patientInfo.setBackground(new Color(230, 247, 255));
        patientInfo.setFocusPainted(false);
        patientInfo.setBorderPainted(false);

        JButton doctorInfo = new JButton();
        doctorInfo.setIcon(doctorIcon);
        doctorInfo.setBounds(520, 0, 400, 400);
        doctorInfo.setBackground(new Color(230, 247, 255));
        doctorInfo.setFocusPainted(false);
        doctorInfo.setBorderPainted(false);


        JButton staffInfo = new JButton();
        staffInfo.setIcon(staffIcon);
        staffInfo.setBounds(1010, 0, 400, 400);
        staffInfo.setBackground(new Color(230, 247, 255));
        staffInfo.setFocusPainted(false);
        staffInfo.setBorderPainted(false);

        JButton inventoryInfo = new JButton();
        inventoryInfo.setIcon(inventoryIcon);
        inventoryInfo.setBounds(30, 435, 400, 400);
        inventoryInfo.setBackground(new Color(230, 247, 255));
        inventoryInfo.setFocusPainted(false);
        inventoryInfo.setBorderPainted(false);

        JButton billingInfo = new JButton();
        billingInfo.setIcon(billingIcon);
        billingInfo.setBounds(520, 435, 400, 400);
        billingInfo.setBackground(new Color(230, 247, 255));
        billingInfo.setFocusPainted(false);
        billingInfo.setBorderPainted(false);

        JButton AppointmentInfo = new JButton();
        AppointmentInfo.setIcon(appointIcon);
        AppointmentInfo.setBounds(1010, 435, 400, 400);
        AppointmentInfo.setBackground(new Color(230, 247, 255));
        AppointmentInfo.setFocusPainted(false);
        AppointmentInfo.setBorderPainted(false);


        //mouse events
        patientInfo.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                patientInfo.setBounds(20, 10, 420, 420);
            }
            public void mouseExited(MouseEvent e){
                patientInfo.setBounds(30, 0, 400, 400);
            }
            public void mousePressed(MouseEvent e){
                patientInfo.setBounds(30, 0, 400, 400);
            }
        });
        doctorInfo.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                doctorInfo.setBounds(510, 10, 420, 420);
            }
            public void mouseExited(MouseEvent e){
                doctorInfo.setBounds(520, 0, 400, 400);
            }
            public void mousePressed(MouseEvent e){
                doctorInfo.setBounds(520, 0, 400, 400);
            }
        });
        staffInfo.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                staffInfo.setBounds(1000, 10, 420, 420);
            }
            public void mouseExited(MouseEvent e){
                staffInfo.setBounds(1010, 0, 400, 400);
            }
            public void mousePressed(MouseEvent e){
                staffInfo.setBounds(1010, 0, 400, 400);
            }
        });
        inventoryInfo.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                inventoryInfo.setBounds(20, 440, 420, 420);
            }
            public void mouseExited(MouseEvent e){
                inventoryInfo.setBounds(30, 435, 400, 400);
            }
            public void mousePressed(MouseEvent e){
                inventoryInfo.setBounds(30, 435, 400, 400);
            }
        });
        billingInfo.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                billingInfo.setBounds(510, 440, 420, 420);
            }
            public void mouseExited(MouseEvent e){
                billingInfo.setBounds(520, 435, 400, 400);
            }
            public void mousePressed(MouseEvent e){
                billingInfo.setBounds(520, 435, 400, 400);
            }
        });
        AppointmentInfo.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                AppointmentInfo.setBounds(1000, 440, 420, 420);
            }
            public void mouseExited(MouseEvent e){
                AppointmentInfo.setBounds(1010, 435, 400, 400);
            }
            public void mousePressed(MouseEvent e){
                AppointmentInfo.setBounds(1010, 435, 400, 400);
            }
        });
        

        //action listener
        doctorInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new DoctorManagement();
            }
        });
        patientInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new PatientManagement();
            }
        });
        staffInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new StaffManagement();
            }
        });
        billingInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new BillingManagement();
            }
        });
        inventoryInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new InventoryManagement();
            }
        });
        AppointmentInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new AppointmentBookingMainFrame();
            }
        });

        //frame add
        menuFrame.add(patientInfo);
        menuFrame.add(doctorInfo);
        menuFrame.add(staffInfo);
        menuFrame.add(AppointmentInfo);
        menuFrame.add(inventoryInfo);
        menuFrame.add(billingInfo);

        //visability
        menuFrame.setVisible(true);
        menuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public static void main(String[] args) {
        new MenuFrame();
    }
}