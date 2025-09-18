import javax.swing.*;
import java.sql.*;

class Global {
    public static String url = "jdbc:mysql://localhost:3306/MedManage";
    public static String userName = "root";
    public static String password;
    public Global(String passwd) {
        password = passwd;
    }
}

public class DBConnection {
    // Image sources
    ImageIcon enterIcon = new ImageIcon(DBConnection.class.getResource("Enter.png"));
    ImageIcon errorIcon = new ImageIcon(DBConnection.class.getResource("Error.png"));

    public static Connection con;

    public DBConnection() {
        String pass = null;
        Connection connection = null;
        while (connection == null) {
            while (pass == null || pass.isEmpty()) {
                pass = (String) JOptionPane.showInputDialog(
                    null,
                    "Enter user Database password",
                    "Password",
                    JOptionPane.PLAIN_MESSAGE,
                    enterIcon,
                    null,
                    ""
                );
                if (pass == null) {
                    return;
                }
            }
            try {
                connection = DriverManager.getConnection(Global.url, Global.userName, pass);
                if (connection != null) {
                    new Global(pass);
                    con = connection;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Incorrect password",
                    "Error",
                    JOptionPane.ERROR_MESSAGE,
                    errorIcon
                );
                pass = null;
            }
        }
    }

    public static void main(String[] args) {
        new DBConnection();
    }
}
