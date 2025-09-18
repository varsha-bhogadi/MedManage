import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InventoryManagement {

    //image sources
    ImageIcon logo2 = new ImageIcon(InventoryManagement.class.getResource("MedManageLogo.png"));
    ImageIcon checkIcon = new ImageIcon(InventoryManagement.class.getResource("Check.png"));
    ImageIcon logoSmall = new ImageIcon(InventoryManagement.class.getResource("logoSmall.png"));
    ImageIcon doctorAddIcon = new ImageIcon(InventoryManagement.class.getResource("StockManagementAdd.png"));
    ImageIcon doctorRemoveIcon = new ImageIcon(InventoryManagement.class.getResource("StockManagementRemove.png"));
    ImageIcon doctorSearIcon = new ImageIcon(InventoryManagement.class.getResource("StockManagementSearch.png"));
    ImageIcon doctorListIcon = new ImageIcon(InventoryManagement.class.getResource("StockManagementList.png"));
    ImageIcon enterIcon = new ImageIcon(InventoryManagement.class.getResource("Enter.png"));

    public InventoryManagement(){
 
        //frame creation
        JFrame inventoryManagement = new JFrame();
        inventoryManagement.setTitle("Inventory Management");
        inventoryManagement.setSize(1440, 900);
        inventoryManagement.setLocationRelativeTo(null);
        inventoryManagement.setResizable(false);
        inventoryManagement.setLayout(null);
        inventoryManagement.getContentPane().setBackground(new Color(230, 247, 255));
        inventoryManagement.setIconImage(logo2.getImage());

        //Label
        JLabel medLabel = new JLabel();
        medLabel.setText("INVENTORY MANAGEMENT");
        medLabel.setBounds(100, 50, 1440, 150);
        medLabel.setFont(new Font("Times new roman", Font.BOLD, 95)); 
        medLabel.setForeground(Color.BLACK);

        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logoSmall);
        logoLabel.setBounds(0, 50, 200, 150);
        

        //Option buttons
        JButton stockadd = new JButton();
        stockadd.setBounds(50, 350, 250, 250);
        stockadd.setIcon(doctorAddIcon);
        stockadd.setBackground(new Color(230, 247, 255));
        stockadd.setFocusPainted(false);
        stockadd.setBorderPainted(false);

        JButton stockRemove = new JButton();
        stockRemove.setBounds(400, 350, 250, 250);
        stockRemove.setIcon(doctorRemoveIcon);
        stockRemove.setBackground(new Color(230, 247, 255));
        stockRemove.setFocusPainted(false);
        stockRemove.setBorderPainted(false);

        JButton stockSearch = new JButton();
        stockSearch.setBounds(770, 350, 250, 250);
        stockSearch.setIcon(doctorSearIcon);
        stockSearch.setBackground(new Color(230, 247, 255));
        stockSearch.setFocusPainted(false);
        stockSearch.setBorderPainted(false);

        JButton stockList = new JButton();
        stockList.setBounds(1140, 350, 250, 250);
        stockList.setIcon(doctorListIcon);
        stockList.setBackground(new Color(230, 247, 255));
        stockList.setFocusPainted(false);
        stockList.setBorderPainted(false);

        //mouse events
        stockadd.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                stockadd.setBounds(35, 340, 280, 280);
            }
            public void mouseExited(MouseEvent e){
                stockadd.setBounds(50, 350, 250, 250);
            }
            public void mousePressed(MouseEvent e){
                stockadd.setBounds(50, 350, 250, 250);
            }
        });

        stockRemove.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                stockRemove.setBounds(385, 340, 280, 280);
            }
            public void mouseExited(MouseEvent e){
                stockRemove.setBounds(400, 350, 250, 250);
            }
            public void mousePressed(MouseEvent e){
                stockRemove.setBounds(400, 350, 250, 250);
            }
        });

        stockSearch.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                stockSearch.setBounds(755, 340, 280, 280);
            }
            public void mouseExited(MouseEvent e){
                stockSearch.setBounds(770, 350, 250, 250);
            }
            public void mousePressed(MouseEvent e){
                stockSearch.setBounds(770, 350, 250, 250);
            }
        });

        stockList.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                stockList.setBounds(1125, 340, 280, 280);
            }
            public void mouseExited(MouseEvent e){
                stockList.setBounds(1140, 350, 250, 250);
            }
            public void mousePressed(MouseEvent e){
                stockList.setBounds(1140, 350, 250, 250);
            }
        });

        //action listener
        // Action Listener
stockadd.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String[] options = {"choose one", "Add Stock", "Update Stock"};
        JComboBox<String> chooseAddOrUpdate = new JComboBox<>(options);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Select an option:"));
        panel.add(chooseAddOrUpdate);

        // Creating the JOptionPane
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, enterIcon);
        JDialog dialog = optionPane.createDialog("Add or Update Stock");
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
            if (selectedOption.equals("Add Stock")) new InventoryManagementAdd();
            if (selectedOption.equals("Update Stock")) new InventoryManagementUpdate();
        }
    }
});

        stockRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new InventoryManagementRemove();
            }
        });
        stockSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new InventoryManagementSearch();
            }
        });
        stockList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new InventoryManagementList();
            }
        });
        //Frame add
        inventoryManagement.add(stockadd);
        inventoryManagement.add(medLabel);
        inventoryManagement.add(logoLabel);
        inventoryManagement.add(stockRemove);
        inventoryManagement.add(stockSearch);
        inventoryManagement.add(stockList);

        //visibility and exit
        inventoryManagement.setVisible(true);
        inventoryManagement.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public static void main(String[] args) {
        new InventoryManagement();
    }
}
