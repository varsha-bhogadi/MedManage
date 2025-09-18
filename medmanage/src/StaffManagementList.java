import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import java.awt.*;

public class StaffManagementList {
    public StaffManagementList() {
        try {
            Connection con = DriverManager.getConnection(Global.url,Global.userName,Global.password);
            if (con != null) {

                // Window creation
                JFrame viewTableFrame = new JFrame();
                viewTableFrame.setResizable(false);
                viewTableFrame.setLayout(null);
                viewTableFrame.setTitle("Staff Table");
                viewTableFrame.getContentPane().setBackground(new Color(230, 247, 255));
                viewTableFrame.setSize(1440, 900);
                viewTableFrame.setLocationRelativeTo(null);

                // Icon add
                ImageIcon img = new ImageIcon(StaffManagementList.class.getResource("MedManageLogo.png"));
                viewTableFrame.setIconImage(img.getImage());

                // Database query execution and data storing
                Statement st = con.createStatement();

                ResultSet rs = st.executeQuery("SELECT * FROM Staff");

                // Columns names and storing
                ResultSetMetaData attributeData = rs.getMetaData();
                int columnCount = attributeData.getColumnCount();

                String[] cNames = new String[columnCount];
                for(int i = 1;i<=columnCount;i++){
                    cNames[i-1]=attributeData.getColumnName(i);
                }

                //column data
                String[][] data = new String[1000][columnCount];
                int rowIndex = 0;
                while (rs.next()) {
                    for(int i=1;i<=columnCount;i++){
                        data[rowIndex][i-1]=rs.getString(i);
                    }
                    rowIndex++;
                }

            
                //Overriding isCellEditable method to make cells non editable
                DefaultTableModel model = new DefaultTableModel(data, cNames) {
                    public boolean isCellEditable(int row, int column) {
                        return false; 
                    }
                };

                //table creating
                JTable table = new JTable(model);
                table.setRowHeight(30);

                //Set font
                Font font = new Font("Times New Roman", Font.BOLD, 18);
                table.setFont(font);

                // Creating scroll panel
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBounds(10, 10, 1410, 850);
                scrollPane.getViewport().setBackground(new Color(230, 247, 255));

                // Visibility
                viewTableFrame.setVisible(true);
                viewTableFrame.add(scrollPane, BorderLayout.CENTER);
                viewTableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Table not found.");
        }
    }
    public static void main(String[] args) {
        new StaffManagementList();
    }
}