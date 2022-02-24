import java.sql.*;

public class DataBase {

    public static void createNewDatabase(String fileName) {

        String dbURL = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(dbURL)) {
            if (conn == null) { // if no database with such name created - creating a new one
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable(String fileName, String tableName) {

        String dbURL = "jdbc:sqlite:" + fileName;

        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                + "	id INTEGER,\n"
                + "	number TEXT NOT NULL,\n"
                + "	pin TEXT,\n"
                + " balance INTEGER DEFAULT 0"
                + ");";

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean checkAccount(String fileName, String tableName, String cardNumber) {
        String dbURL = "jdbc:sqlite:" + fileName;

        boolean checkAccountExistion = false;

        String query = "SELECT * FROM " + tableName + " WHERE number = " + cardNumber;
        try (Connection conn = DriverManager.getConnection(dbURL)) {
            // Statement creation
            try (Statement statement = conn.createStatement()) {
                // Statement execution
                ResultSet rs = statement.executeQuery(query);
                checkAccountExistion = rs.next(); // checking if entry exist
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return checkAccountExistion;
    }

    public static void insertAccount(String fileName, String tableName, String Cardnumber, String pin) {

    }

    public static boolean checkLogin() {
        return false;
    }
}
