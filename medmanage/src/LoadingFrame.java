import java.awt.*;

import javax.swing.*;

public class LoadingFrame extends Thread {
    //frame label initiallization
    JLabel loading;
    JFrame loadingFrame;

    //thread loading effect
    public void run() {
        for (int i = 1; i <= 4; i++) {
            String txt = "Loading   ";
            if (i == 2) txt = "Loading.  ";
            if (i == 3) txt = "Loading.. ";
            if (i == 4) txt = "Loading...";
            loading.setText(txt);
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        loadingFrame.dispose();
    }

    public LoadingFrame() {

        //image
        ImageIcon logo = new ImageIcon(LoadingFrame.class.getResource("MedManageLogo.png"));
        ImageIcon logo2 = new ImageIcon(LoadingFrame.class.getResource("logoSmall.png"));

        // Loading frame creation
        loadingFrame = new JFrame();
        loadingFrame.setSize(1440,900);
        loadingFrame.setLocationRelativeTo(null);
        loadingFrame.setTitle("Loading...");
        loadingFrame.setResizable(false);
        loadingFrame.getContentPane().setBackground(new Color(230, 247, 255));
        loadingFrame.setIconImage(logo.getImage());
        loadingFrame.setLayout(null);

        // Fonts
        Font f1 = new Font("Times New Roman", Font.BOLD, 60);

        // Label creation
        loading = new JLabel();
        // loading.setSize(200, 100);
        loading.setBounds(580, 310, 300, 300);
        loading.setFont(f1);

        JLabel icon = new JLabel(logo2);
        icon.setBounds(650, 300, 100, 100);

        //frame add
        loadingFrame.add(icon);
        loadingFrame.add(loading);

        // Thread
        Thread load = new Thread(this);
        load.start();

        // Visibility
        loadingFrame.setVisible(true);
        loadingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new LoadingFrame();
    }
}
