import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class database {

    private static final String URL = "jdbc:mysql://localhost:3306/sales_record";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.err.println("Database connection error: " + e.getMessage());
            return null;
        }
    }

    public static void loadSalesData(JTable table) {
        String query = "SELECT product_name, stock, price FROM sales";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear previous data

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("product_name"),
                    rs.getInt("stock"),
                    rs.getDouble("price")
                });
            }

        } catch (Exception e) {
            System.err.println("Error loading sales data: " + e.getMessage());
        }
    }
}
