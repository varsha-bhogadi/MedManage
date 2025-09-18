import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BannerFrame {
    public BannerFrame() {

        // Image sources
        ImageIcon banner = new ImageIcon(BannerFrame.class.getResource("BannerFrameBanner.png"));
        ImageIcon logo1 = new ImageIcon(BannerFrame.class.getResource("MedManageLogo.png"));
        ImageIcon buttonimg = new ImageIcon(BannerFrame.class.getResource("MenuButton.png"));
        ImageIcon logosmall = new ImageIcon(BannerFrame.class.getResource("logoSmall.png"));


        // Loading page
        new LoadingFrame(); 
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        // Frame creation
        JFrame bannerFrame = new JFrame();
        bannerFrame.setSize(1440, 900);
        bannerFrame.setLocationRelativeTo(null);
        bannerFrame.setResizable(false);
        bannerFrame.setLayout(null);
        bannerFrame.setTitle("MedManage");
        bannerFrame.getContentPane().setBackground(new Color(230, 247, 255));
        bannerFrame.setIconImage(logo1.getImage());

        // Button
        JButton menuButton = new JButton();
        menuButton.setBounds(900, 300, 300, 130);
        menuButton.setBackground(Color.WHITE);
        menuButton.setFocusPainted(false);
        menuButton.setIcon(buttonimg);
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(false);

        // ActionListener
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MenuFrame();
            }
        });

        // MouseListener for hover effect
        menuButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                menuButton.setBounds(900, 300, 300, 180); 
            }

            
            public void mouseExited(MouseEvent e) { 
                menuButton.setBounds(900, 300, 300, 130);
            }
            public void mousePressed(MouseEvent e){
                menuButton.setBounds(900, 300, 300, 130);
            }
        });

        // Labels
        JLabel bannerLabel = new JLabel();
        bannerLabel.setIcon(banner);
        bannerLabel.setBounds(0, 0, 1440, 900);

        JLabel textLabel = new JLabel("MED MANAGE");
        textLabel.setBounds(400, 10, 1000, 150); 
        textLabel.setFont(new Font("Times new roman", Font.BOLD, 100)); 
        textLabel.setForeground(Color.BLACK);

        JLabel logo = new JLabel();
        logo.setIcon(logosmall);
        logo.setBounds(300, 30, 100, 100);

        //frame add
        bannerFrame.add(logo);
        bannerFrame.add(menuButton);
        bannerFrame.add(textLabel);
        bannerFrame.add(bannerLabel); 

        // Visibility and exiting
        bannerFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        bannerFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null, 
                    "Are you sure you want to exit?", "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,logosmall);

                if (confirmed == JOptionPane.YES_OPTION) {
                    bannerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    bannerFrame.dispose();
                }
            }
        });
        bannerFrame.setVisible(true);
        new DBConnection();
        if(DBConnection.con==null) bannerFrame.dispose();
    }

    public static void main(String[] args) {
        new BannerFrame();
    }
}