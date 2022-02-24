import java.sql.*;

public class DataBase {

    private static int rowCount;

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

        String countNumberOfRows = "SELECT COUNT(1) FROM " + tableName + ";";

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql); // create a new table executing query

            ResultSet rs = stmt.executeQuery(countNumberOfRows); // count number of rows
            rowCount = rs.last() ? rs.getRow() : 0; // if we do have rows in result set - we return number of rows, otherwise - we return 0;
            System.out.println("Number of rows: " + rowCount);
            rs.beforeFirst(); // moving cursor to the beginning;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public static boolean checkAccount(String fileName, String tableName, String cardNumber) {
        String dbURL = "jdbc:sqlite:" + fileName;

        boolean checkAccountExistion = false;

        String query = "SELECT * FROM " + tableName + " WHERE number = " + cardNumber;

        try (Connection conn = DriverManager.getConnection(dbURL);
            Statement statement = conn.createStatement()) {
                // Statement execution
                ResultSet rs = statement.executeQuery(query);
                checkAccountExistion = rs.next(); // checking if entry exist
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return checkAccountExistion;
    }

    public static void insertAccount(String fileName, String tableName, String cardNumber, String pin) {
        String dbURL = "jdbc:sqlite:" + fileName;

        boolean checkAccountExistion = false;

        rowCount++;
        String query = "INSERT INTO " + tableName + "(id, number, pin) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {


            pstmt.setInt(1, rowCount);
            pstmt.setString(2, cardNumber);
            pstmt.setString(3, pin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkLogin() {
        return false;
    }
}
